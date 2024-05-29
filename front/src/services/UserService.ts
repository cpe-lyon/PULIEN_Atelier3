
const UserService = {
    getUserCash : async (): Promise<any> => {
        try {

            const token = localStorage.getItem('auth');
            const bearerToken = 'Bearer '+ token;


            const request = new Request('http://localhost:10001/user/current', {
                method: 'POST',
                mode: 'no-cors',
                headers: new Headers({ 'Content-Type': 'application/json' }),
                body : JSON.stringify({ "token" : bearerToken})
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
        } catch (error) {
            throw error;
        }
    },
    getUser : async (): Promise<any> => {
        try {

            const token = localStorage.getItem('auth');
            const bearerToken = 'Bearer '+ token;


            const request = new Request('http://localhost:10001/user/current', {
                method: 'GET',
                headers: new Headers({ 'Content-Type': 'application/json', 'Authorization': bearerToken }),
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
        } catch (error) {
            throw error;
        }
    }
}


export default UserService;