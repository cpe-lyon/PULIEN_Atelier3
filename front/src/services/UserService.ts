import {MS_USER_PORT} from "@/services/constants.ts";

const UserService = {
    getUserCash : async (): Promise<any> => {
        const token = localStorage.getItem('auth');
        const request = new Request(`http://localhost:${MS_USER_PORT}/user/current`, {
            method: 'GET',
            headers: new Headers({ 'Content-Type': 'application/json', 'Authorization': token || '' }),
        });

        return fetch(request)
            .then(response => {
                if (response.status < 200 || response.status >= 300) {
                    throw new Error(response.statusText);
                }
                console.log(response);
                return response.json().then( i => i.cash);
            })
            .catch(() => {
                throw new Error('Network error')
            });
    },

    getUser : async (): Promise<any> => {
        const token = localStorage.getItem('auth');

        const request = new Request(`http://localhost:${MS_USER_PORT}/user/current`, {
            method: 'GET',
            headers: new Headers({ 'Content-Type': 'application/json', 'Authorization': token || ''}),
        });

        return fetch(request)
            .then(response => {
                if (response.status < 200 || response.status >= 300) {
                    throw new Error(response.statusText);
                }
                return response.json()
            })
            .catch(() => {
                throw new Error('Network error')
            });
    }
}


export default UserService;