//import { Avatar, AvatarFallback, AvatarImage } from "./components/ui/avatar";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useEffect, useState } from "react";


export default function Panel() {

    const [chatElement, setChatElement] = useState<React.ReactNode[]>([]);

    const chatFetch = async () : Promise<string[]> => {
        const chatLogsResponse = await fetch("http://localhost:8080/api/data/panel", {
                method: "GET",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json"
                }
        });
        if(chatLogsResponse.ok) {
            const rawChatLogs: string[] = await chatLogsResponse.json();
            return rawChatLogs;
        }
        return [];
    }

    useEffect(() => {
        console.log("Running connection...")
        const load = async () => {
            let data :string[] = await chatFetch();

            while(data.length < 20) {
                data = ["\u200B", ...data];
            }
            
            const panelChatElement = data.map((element, index) => (
                <div key = {index}>
                    {element}
                </div>
            ));
            setChatElement(panelChatElement);
        }; 
        load();
    }, []);

    return (
        <>
            <div className="flex items-center justify-center mt-4">
                <Card className="w-[95%]">
                    <CardHeader>
                        <CardTitle>Server chat</CardTitle>
                    </CardHeader>
                    <CardContent>
                        {chatElement}
                    </CardContent>
                </Card>
            </div>
        </>
    );
}