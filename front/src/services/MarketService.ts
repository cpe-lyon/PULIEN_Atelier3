import {MS_CARDS_PORT} from "@/services/constants.ts";

const MarketService = {
    getCardInstanceBuyable: async (): Promise<any> => {
        const token = localStorage.getItem('auth');
        const request = new Request(`http://localhost:${MS_CARDS_PORT}/marketPlace`, {
            method: 'GET',
            headers: new Headers({'Content-Type': 'application/json', 'Authorization': token || ''}),
        });

        return fetch(request)
            .then(response => {
                if (response.status < 200 || response.status >= 300) {
                    throw new Error(response.statusText);
                }
                return response.json()
            })
            .then(res => {
                return res
            })
            .catch(() => {
                throw new Error('Network error')
            });
    },

    buyCardInstanceBuyable: async (id: number): Promise<any> => {
        const bearerToken = localStorage.getItem('auth');
        const url = `http://localhost:${MS_CARDS_PORT}/marketPlace/buy/${id}`

        const request = new Request(url, {
            method: 'POST',
            headers: new Headers({'Content-Type': 'application/json', 'Authorization': bearerToken || ''}),
        });

        return fetch(request)
            .then(response => {
                if (response.status < 200 || response.status >= 300) {
                    throw new Error(response.statusText);
                }
                return response.json()
            })
            .then(res => {
                return res
            })
            .catch(() => {
                throw new Error('Network error')
            });
    },

    sellCardInstance: async (id: number): Promise<any> => {
        const bearerToken = localStorage.getItem('auth');
        const url = `http://localhost:${MS_CARDS_PORT}/marketPlace/sell/${id}`;

        const request = new Request(url, {
            method: 'POST',
            headers: new Headers({'Content-Type': 'application/json', 'Authorization': bearerToken || ''}),
        });

        return fetch(request)
            .then(response => {
                if (response.status < 200 || response.status >= 300) {
                    throw new Error(response.statusText);
                }
                return response.json()
            })
            .then(res => {
                return res
            })
            .catch(() => {
                throw new Error('Network error')
            });
    },
}


export default MarketService;