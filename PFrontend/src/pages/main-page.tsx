import { Button } from "@/components/ui/button";
import { Card, CardTitle, CardHeader, CardDescription, CardAction, CardContent, CardFooter } from "@/components/ui/card";
import NavigateBar from "@/components/elem/navigate-bar";
import Footer from "@/components/elem/footer";
//import { Link } from "react-router-dom";
//import { Avatar, AvatarFallback, AvatarImage } from "./components/ui/avatar";


  export default function MainPage() {
    
    const logged = localStorage.getItem("logged");
    return (
      <div className="bg-gray-100">
        <NavigateBar />
        
        {/*Main page*/}
        <main className="mx-[5%]">
            {/*Main description*/}
            <div className="flex flex-col items-center justify-center my-20">
              <h1 className="text-6xl font-bold gray-900">Your public panel</h1>
              <div className="mt-5 gray-900 text-2xl">An interactive, public dashboard designed for your minecraft server,</div>
              <div className="gray-900 text-2xl">allowing everyone to observe informations, chat and so on!</div>
              
              {logged ? (
                <Button variant="outline" className="mt-5 bg-blue-300">Go to panel</Button>
              ) : (
                <div className="flex gap-4">
                  <Button variant="outline" className="mt-5 bg-blue-300">Log in</Button>
                  <Button variant="outline" className="mt-5 bg-blue-300">Sign in</Button>
                </div>
              )}
            </div>
            {/*Cards*/}
            <div className="flex items-center justify-center gap-4 items-stretch">
              <Card className="flex-1 border-2 hover:border-blue-400 transition-colors duration-300">
                <CardHeader>
                  <CardTitle className = "text-2xl">About project</CardTitle>
                  <CardDescription>Project description, freatures and plans</CardDescription>
                </CardHeader>
                <CardContent className="text-[16px]">
                  That's project is an interactive panel for minecraft servers which want to share access to some freatures like chat views for every user thoughtout this page. In the future we plan to add some new freatures like money for buy things in itemshop and broadcasts with visibility depending on the role, access to some account settings and so on!
                </CardContent>
              </Card>
              <Card className="flex-1 border-2 hover:border-blue-400 transition-colors duration-300">
                <CardHeader>
                  <CardTitle className = "text-2xl">About author</CardTitle>
                  <CardDescription>Author description and links</CardDescription>
                </CardHeader>
                <CardContent className="text-[16px] flex flex-col">
                  That's panel was created by Adam Menkiel, backend was created in Java + SpringBoot + Netty, frontend is created in Vite + React + TypeScript, that's opensource project which is possible to download at adammenkiel github.
                  <div className="flex">
                    <a href="https://github.com/adammenkiel/JWebPanel" className="mt-2 text-blue-800 text-[16px]">Link to project</a>
                  </div>
                  <a href="https://github.com/adammenkiel/" className="text-blue-800 text-[16px]">Link to author's github</a>
                </CardContent>
              </Card>
            </div>
        </main>
        <Footer />
      </div>
    );
  }