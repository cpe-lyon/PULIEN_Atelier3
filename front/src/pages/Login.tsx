import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { useForm } from "react-hook-form"

import { Button } from "@/components/ui/button"
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "@/components/ui/form"
import { Input } from "@/components/ui/input"
import authProvider from "@/services/AuthProvider"
import { Link, Navigate, useNavigate } from "react-router-dom"
import {useAtom} from "jotai";
import {token as contextToken, userCash} from "@/context/jotai.ts";
import UserService from "@/services/UserService.ts";

const formSchema = z.object({
    login: z.string().min(6,{
        message: "Login need to contain at least 6 characters",
    }),
    password: z.string().min(6,{
        message: "Password need to contain at least 6 characters",
    }),
})

const LoginForm = () => {
    // eslint-disable-next-line react-hooks/rules-of-hooks
    const navigate = useNavigate();
    const [token, setToken] = useAtom(contextToken);
    const [cash, setCash] = useAtom(userCash);

    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
        defaultValues: {
            name: "",
        },
    })

    async function onSubmit(values: z.infer<typeof formSchema>) {
        const login : string = values['login']
        const password : string = values['password']

        const response = await authProvider.login({username : login, password : password});

        response ? setToken(response) : console.error("ERROR WHILE STORING TOKEN IN CONTEXT");

        const cashResponse = await UserService.getUserCash();

        cashResponse ? setCash(cashResponse) : console.error("ERROR WHILE GETTING USER CASH");

        navigate("/");
    }

    function isConnected(){
        return authProvider.checkAuth();
    }

    return !isConnected() ? (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-5 w-1/5 mx-auto mt-64">
                <FormField
                    control={form.control}
                    name="login"
                    render={({field}) => (
                        <FormItem>
                            <FormLabel>Identifiant</FormLabel>
                            <FormControl>
                                <Input placeholder="Identifiant" {...field}/>
                            </FormControl>
                            <FormMessage/>
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="password"
                    render={({field}) => (
                        <FormItem>
                            <FormLabel>Mot de passe</FormLabel>
                            <FormControl>
                                <Input placeholder="Mot de passe" type={"password"} {...field} />
                            </FormControl>
                            <FormMessage/>
                        </FormItem>
                    )}
                />
                <div>
                    <Link to="/register">Créer un compte</Link>
                </div>
                <Button type="submit" className="w-full">Connexion</Button>
            </form>
        </Form>
    ) :  <Navigate to="/authentified" />
}

export default LoginForm;