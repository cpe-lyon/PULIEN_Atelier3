import CardDetails from "@/components/cardDetails";
import { CardInstance } from "./JSON/CardInstance";
import { ResponseModel } from "./JSON/ResponseModel";

const CardInstanceContainer = ({ cardInstanceBuyable, dialogToBuy }: any) => {
    console.log(JSON.stringify(cardInstanceBuyable));
    const cardInstanceJson = cardInstanceBuyable;
    const responseModel = ResponseModel.fromJson(cardInstanceJson[0]);
    console.log(responseModel);

    console.log("---------------------");
    console.log(responseModel);
    console.log("---------------------");
    responseModel.content.map((card) => {
        console.log(card);
    });
    console.log("000000000000000000000");

    return (
        <div className="w-full flex justify-center items-center py-4">
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 w-[90%]">
            {responseModel.content.map((cardInstance: CardInstance) => {
                const card = cardInstance.card;
                return (
                    <div className="flex justify-center items-center">
                        <CardDetails
                            id={cardInstance.id}
                            key={card.cardId}
                            country={card.nation || "Earth"}
                            nameCard={card.name}
                            height={card.height}
                            weight={card.weight}
                            pace={card.pace}
                            rate={card.rating}
                            proprio={cardInstance.user.login}
                            cardInstanceId={cardInstance.id}
                            buyable={cardInstance.isBuyable}
                            onClickOnBuy={() => dialogToBuy(cardInstance.id)}
                        />
                    </div>
                );
            })}
        </div>
     </div>
);
};

export default CardInstanceContainer;




{/* {cardInstanceBuyable.map((ci: any) => {
                // Check if ci.card is defined
                if (!ci.card) {
                    return null; // or handle this case appropriately
                }
                const card: Card = ci.card; // Ensure card is defined properly
                return (
                    <CardDetails
                        id={ci.id}
                        key={ci.id}
                        country={card.nation || "Earth"}
                        nameCard={card.name}
                        height={card.height}
                        weight={card.weight}
                        pace={card.pace}
                        rate={card.rating}
                        proprio={ci.login}
                        cardInstanceId={ci.id}
                        buyable={true}
                        onClickOnBuy={() => dialogToBuy(ci.id)}
                    />
                );
            })} */}