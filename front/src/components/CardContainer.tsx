import CardDetails from "@/components/cardDetails";

const CardContainer = ({cards}:any) => (
    <div className="card-container mx-10">
        {cards.map((ci:any) => (
            <CardDetails
                key={ci.id}
                country={ci.card.nation==undefined?"Earth":ci.nation}
                nameCard={ci.name}
                height={ci.height}
                weight={ci.weight}
                pace={ci.pace}
                rate={ci.rating}
                proprio={ci.user.login}
                id={ci.id}
                cardInstanceId={ci.id}
                onClickOnBuy={ci.id}
                buyable={false}
            />
        ))}
    </div>
);

export default CardContainer;