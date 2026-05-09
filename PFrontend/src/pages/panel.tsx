//import { Avatar, AvatarFallback, AvatarImage } from "./components/ui/avatar";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useEffect, useState } from "react";


export default function Panel() {

    type ResponseType = "ok" | "err" | null;
    const [responseType, setResponseType] = useState<ResponseType>(null);
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
            setResponseType("ok");
            return rawChatLogs;
        }
        if(chatLogsResponse.status == 403) {
            setResponseType("err");
            localStorage.removeItem("logged");
            localStorage.removeItem("username");
            window.location.href = "/";
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
                <div className="" key = {index}>
                    {element}
                </div>
            ));
            setChatElement(panelChatElement);
        }; 
        load();
        let keyNumber = 20;
        const socket = new WebSocket("ws://localhost:8080/ws");

        socket.onopen = () => {
          console.log("Connected");
        };

        socket.onmessage = (event) => {
          console.log("Received:", event.data);
          const dataMessage :string = event.data;

          setChatElement(prev =>
                [...prev,
                    (
                        <div className="" key = {keyNumber}>
                            {dataMessage}
                        </div>
                    )
                ]
            );
            setChatElement(prev => prev.slice(1));
            keyNumber = keyNumber + 1;
        };

        socket.onclose = () => {
          console.log("Disconnected");
        };
        return () => socket.close();
    }, []);

    if(responseType !== "ok") {
        return (<></>);
    }

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