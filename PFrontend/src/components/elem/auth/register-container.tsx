import { 
    Card,
    CardTitle, 
    CardHeader, 
    CardDescription, 
    CardAction, 
    CardContent, 
    CardFooter
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useState } from "react";
import { useAuthContext } from "./auth-context";


export default function RegisterContainer() {

    type Response = "error" | null;

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [response, setResponse] = useState<Response>(null);

    const fetchRegister = async (username: string, email: string, password: string) => {
        try {
            const authInfo = await fetch("http://localhost:8080/api/auth/register", {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    "username": username,
                    "email": email,
                    "password": password
                })
            });
            if(authInfo.ok) {
                window.location.reload();
            } else {
                setResponse("error");
            }

        } catch (error) {
            console.error(error);
        }
    };

    const { setView } = useAuthContext();

    return (
        <div onClick={() => setView(null)} className="fixed inset-0 bg-black/50 backdrop-blur-sm flex items-center justify-center z-60">
            <Card onClick={(event) => event.stopPropagation()} className="border-2 w-80">
                <CardHeader>
                    <CardTitle>Sign up</CardTitle>
                    <CardDescription>Please type your username, email and password to create new account.</CardDescription>
                    <CardAction className="hover-text cursor-pointer"
                        onClick={() => {setView("login")}}>Log in</CardAction>
                </CardHeader>
                <CardContent>
                    <div className="flex flex-col gap-2">
                        <Input onChange={(event) => setUsername(event.target.value)} type="text" placeholder="Username"></Input>
                        <Input onChange={(event) => setEmail(event.target.value)} type="text" placeholder="Email"></Input>
                        <Input onChange={(event) => setPassword(event.target.value)} type="password" placeholder="Password"></Input>
                    </div>
                    {response === "error" && 
                        <div className="text-red-500">
                            Something went wrong.
                        </div>
                    }
                </CardContent>
                <CardFooter>
                    <Button onClick={() => fetchRegister(username, email, password)} variant="default" className={"w-full"}>Register</Button>
                </CardFooter>
            </Card>
        </div>
    );
}