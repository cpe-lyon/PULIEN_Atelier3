import {useEffect, useState} from "react";
import CardService from "../services/CardService.ts";
import {Card} from "@/models/Card";
import CardDetails from "@/components/cardDetails.tsx";
import {username, userCash} from "@/context/jotai.ts";
import { useAtom } from "jotai";
import {CardInstance} from "@/models/CardInstance";
import TableList from "@/components/TableList.tsx";
import LoadingSpinner from "@/components/LoadingSpinner.tsx";

const CardPage = () => {
    const [isLoading,setIsLoading]=useState<boolean>(true)
    const [cards, setCards] = useState<CardInstance[]>([{}]);
    const [cardToDisplay, setCardToDisplay] = useState<Card>({});
    const [usernameFromContext, setUsername] = useAtom(username);
    const [usercashFromContext, setUsercash] = useAtom(userCash);

    function getCards(){
        return CardService.fetchCards();
    }

    useEffect(() => {
        const getAllCards = async () => {
            let data = await getCards();
            setCards(data);
        }

        getAllCards().then(()=>
        setIsLoading(false));
    }, []);

    return(       
    <div className="w-full h-max bg-slate-700  gap-4 card-page-container">
                {isLoading && <div className="w-screen h-screen flex self-center justify-center"><LoadingSpinner /> </div>}
            {!isLoading &&
            <div className="grid grid-cols-3">
            <div className="table-inventory-container col-span-2 ">
                <TableList cards={cards} setCardDetails={setCardToDisplay}/>
            </div>
            <div className="col-span-1 h-full px-2 align-center flex justify-end items-center ">
                {cardToDisplay ?
                    <CardDetails
                        country={cardToDisplay.nation  || ""}
                        nameCard={cardToDisplay.name || ""}
                        height={cardToDisplay.height || 0}
                        weight={cardToDisplay.weight || 0}
                        pace={cardToDisplay.pace || 0}
                        rate={cardToDisplay.rating || 0}
                        proprio={'You'}
                        cardInstanceId={0}
                        id={cardToDisplay.cardId || 0}
                        buyable={false}
                        onClickOnBuy={() => console.log("")}/>
                    :
                    <p className="more-details">Click on a row to get more details on your card</p>
                }
            </div>
        </div>
        
                }
            </div>
        
    )
}

export default CardPage;