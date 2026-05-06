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


export default function LoginContainer() {

    type Response = "success" | "wrongpass" | null;

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [response, setResponse] = useState<Response>(null);

    const fetchLogin = async (username: string, password: string) => {
        try {
            const authInfo = await fetch("http://localhost:8080/api/auth/login", {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    "username": username,
                    "password": password
                })
            });
            if(authInfo.ok) {
                const data = await authInfo.json();
                localStorage.setItem("username", data.username);
                localStorage.setItem("logged", "true");
                window.location.reload();
            } else {
                setResponse("wrongpass");
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
                    <CardTitle>Login to account</CardTitle>
                    <CardDescription>Please type your username and password to log in.</CardDescription>
                    <CardAction className="hover-text cursor-pointer"
                        onClick={() => {setView("register")}}>Register</CardAction>
                </CardHeader>
                <CardContent>
                    <div className="flex flex-col gap-2">
                        <Input type="text" onChange={(event) => {setUsername(event.target.value)}} placeholder="Username"></Input>
                        <Input type="password" onChange={(event) => {setPassword(event.target.value)}} placeholder="Password"></Input>
                    </div>
                    {response === "wrongpass" && 
                        <div className="mt-5 text-red-500">
                            Your username or password is wrong!
                        </div>
                    }
                </CardContent>
                <CardFooter>
                    <Button onClick={() => fetchLogin(username, password)} variant="default" className={"w-full"}>Login</Button>
                </CardFooter>
            </Card>
        </div>
    );
}