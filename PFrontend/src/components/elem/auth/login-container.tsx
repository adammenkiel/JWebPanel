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


export default function LoginContainer() {
    return (
        <div className="fixed inset-0 bg-black/50 backdrop-blur-sm flex items-center justify-center z-60">
            <Card className="border-2 w-80">
                <CardHeader>
                    <CardTitle>Login to account</CardTitle>
                    <CardDescription>Please type your username and password to log in.</CardDescription>
                    <CardAction>Register</CardAction>
                </CardHeader>
                <CardContent>
                    <div className="flex flex-col gap-2">
                        <Input type="text" placeholder="Username"></Input>
                        <Input type="password" placeholder="Password"></Input>
                    </div>
                </CardContent>
                <CardFooter>
                    <Button variant="default" className={"w-full"}>Login</Button>
                </CardFooter>
            </Card>
        </div>
    );
}