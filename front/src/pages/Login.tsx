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
import { useAtom } from "jotai"
import { username } from "@/context/jotai"

const formSchema = z.object({
    login: z.string().min(6,{
        message: "Login need to contain at least 6 characters",
    }),
    password: z.string().min(6,{
        message: "Password need to contain at least 6 characters",
    }),
})

const loginForm = () => {
    // eslint-disable-next-line react-hooks/rules-of-hooks
    const navigate = useNavigate();
    const [usernameFromContext, setUsernameFromContext] = useAtom(username);

    
    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
        defaultValues: {
            name: "",
        },
    })

    async function onSubmit(values: z.infer<typeof formSchema>) {
        const login : string = values['login']
        const password : string = values['password']

        await authProvider.login({username : login, password : password}).then(()=>{
            if(localStorage.getItem('auth')!=null){
                setUsernameFromContext(username as unknown as string)
            }
        });
        navigate("/");
    }

    function isConnected(){
        return authProvider.checkAuth();
    }

    return !isConnected() ? (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-5 flex flex-col justify-center items-center  w-screen  mx-auto">
              <div className="w-1/3 ">
                  <FormField
                    control={form.control}
                    name="login"
                    render={({field}) => (
                        <FormItem>
                            <FormLabel className="text-white">Identifiant</FormLabel>
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
                            <FormLabel className="text-white">Mot de passe</FormLabel>
                            <FormControl>
                                <Input placeholder="Mot de passe" type={"password"} {...field} />
                            </FormControl>
                            <FormMessage/>
                        </FormItem>
                    )}
                />
                <div>
                    <Link to="/register" className="text-white">Créer un compte</Link>
                </div>
                <Button type="submit" className="w-full">Connexion</Button>
                </div>
            </form>
        </Form>
    ) :  <Navigate to="/authentified" />
}

export default loginForm;