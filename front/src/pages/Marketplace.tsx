import { useEffect, useState } from "react";
import ModalDialog from "@/components/MarketPlaceDialog";
import MarketService from "@/services/MarketService";
import UserService from "@/services/UserService";
import MarketPlaceAlert from "@/components/MarketPlaceAlert";
import CardInstanceContainer from "@/components/CardInstanceContainer";
import {useAtom} from "jotai";
import {userCash} from "@/context/jotai.ts";
import { CardInstance } from "@/models/CardInstance";
import LoadingSpinner from "@/components/LoadingSpinner";

const Marketplace = () => {
    const [_, setUsercash] = useAtom(userCash);
    const [cardInstanceBuyable, setCardInstanceBuyable] = useState<CardInstance[]>([]);
    const [displayDialog, setDisplayDialog] = useState({
        display: false,
        price: 0,
        solde: 1000,
        total: -1,
        cardInstanceId: 1
    });
    const [alertProps, setAlertProps] = useState({
        display: false,
        playerName : '',
        owner : '',
        success: false
    })

    const getCardInstanceBuyable = async () => {
        const response:CardInstance = await MarketService.getCardInstanceBuyable();
        return response;
    };

    const buyCardInstanceBuyable = async (id: number) => {

        let success;
        try{
            await MarketService.buyCardInstanceBuyable(id);
            success = true;
            let solde = await UserService.getUserCash();
            setUsercash(solde);
        }catch (e) {
            success = false;
        }
        console.log('achat de carte avec id', id);

        const cardInstance : CardInstance | undefined = cardInstanceBuyable.find(i => i.id === id);

        let alertProps = {
            display: true,
            playerName : cardInstance!.card!.name as unknown as string,
            owner : cardInstance!.user.login,
            success: success
        }

        setAlertProps(alertProps)
        setDisplayDialog({
            display: false,
            price: 0,
            solde: 1000,
            total: -1,
            cardInstanceId:1
        })

        const data = await getCardInstanceBuyable();
        setCardInstanceBuyable([data]);
    };
    const [isLoading,setIsLoading]=useState<boolean>(true)

    const dialogToBuy = async (id:any) => {
        const cardInstance = cardInstanceBuyable.find(i => i.id === id);
        console.log("ID OF THE CARD TO BUY " + id);
        console.log("ID OF THE CARD FINDED  " + cardInstance?.id);
        
        if (!cardInstance) return;
        
        const price = cardInstance!.card!.price;
        const solde = await UserService.getUserCash();
        const total = solde - price!;

        setDisplayDialog({
            display: true,
            price: price!,
            solde: solde,
            total: total,
            cardInstanceId: id
        });
    };

    useEffect(() => {
        const getData = async () => {
            const data = await getCardInstanceBuyable();
            setCardInstanceBuyable([data]);

        };
        getData().then(()=>
            setIsLoading(false));
    }, []);

    return (

        <>
            {isLoading && <div className="w-screen h-screen flex self-center justify-center"><LoadingSpinner /> </div>}
            {!isLoading && <> <MarketPlaceAlert alertProps={alertProps}></MarketPlaceAlert>
            <CardInstanceContainer cardInstanceBuyable={cardInstanceBuyable} dialogToBuy={dialogToBuy} />
            <ModalDialog displayDialog={displayDialog} setDisplayDialog={setDisplayDialog} buyCardInstanceBuyable={buyCardInstanceBuyable} />
            </>}</>
    );
};

export default Marketplace;
