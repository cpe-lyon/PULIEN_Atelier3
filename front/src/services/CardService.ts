import {Card} from "@/models/Card";
import {MS_CARDS_PORT} from "@/services/constants.ts";

export const fetchCards = async (): Promise<any> => {
    const token: string = localStorage.getItem('auth') || '';

    const request = new Request(`http://localhost:${MS_CARDS_PORT}/cardsInstances`, {
        method: 'GET',
        headers: new Headers({ 'Content-Type': 'application/json', 'Authorization': token }),
    });

    return fetch(request)
        .then(response => {
            if (response.status < 200 || response.status >= 300) {
                throw new Error(response.statusText);
            }
            return response.json();
        })
        .catch(() => {
            throw new Error('Network error')
        });
}; // to use: const cards = await fetchCards()

export const getCardDetails = async (id: number): Promise<Card | undefined> => {
    const token: string = localStorage.getItem('auth') || '';

    const request = new Request(`http://localhost:${MS_CARDS_PORT}/cardsInstances/${id}`, {
        method: 'GET',
        headers: new Headers({ 'Content-Type': 'application/json', 'Authorization': token }),
    });

    return fetch(request)
        .then(async response => {
            if (response.status < 200 || response.status >= 300) {
                throw new Error(response.statusText);
            }
            const cardInstance = await response.json();
            return cardInstance.card;
        })
        .catch(() => {
            throw new Error('Network error')
        });
}

const CardService = {
    getAll : async (): Promise<any> => {
        const token = localStorage.getItem('auth') || '';

        const request = new Request(`http://localhost:${MS_CARDS_PORT}/cards/all`, {
            method: 'GET',
            headers: new Headers({ 'Content-Type': 'application/json', 'Authorization': token }),
        });

        return fetch(request)
            .then(response => {
                if (response.status < 200 || response.status >= 300) {
                    throw new Error(response.statusText);
                }
                return response.json();
            })
            .catch(() => {
                throw new Error('Network error')
            });
    },
}

export default CardService;