
import {
  NavigationMenu,
  NavigationMenuContent,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
  NavigationMenuTrigger,
} from "@/components/ui/navigation-menu";

import { Button } from "@/components/ui/button";
import { Card, CardTitle, CardHeader, CardDescription, CardAction, CardContent, CardFooter } from "./components/ui/card";
import { Link } from "react-router-dom";
import { Avatar, AvatarFallback, AvatarImage } from "./components/ui/avatar";
import { SiDiscord, SiFacebook, SiGithub, SiInstagram, SiYoutube } from "@icons-pack/react-simple-icons";


  export default function App() {
    return (
      <div className="bg-gray-100">
        {/* navbar */}
        <div className="flex sticky top-0 shadow w-full h-13 items-center px-4 z-50 bg-white/85 backdrop-blur">
          <div className="mx-1">JWebPanel</div>
          <NavigationMenu>
            <NavigationMenuList>
              <NavigationMenuItem>
                <NavigationMenuLink>
                  Main page
                </NavigationMenuLink>
              </NavigationMenuItem>
              <NavigationMenuItem>
                <NavigationMenuLink>
                  Panel
                </NavigationMenuLink>
              </NavigationMenuItem>
            </NavigationMenuList>
          </NavigationMenu>
          <div className="flex items-center ml-auto">
            <div className = "mr-5">
              <span>Logged as: </span>
              <span className="font-semibold">AdamPL</span>
            </div>
            <Button variant="destructive">Log out</Button>
          </div>
        </div>
        
        {/*Main page*/}
        <main className="mx-[5%]">
            {/*Main description*/}
            <div className="flex flex-col items-center justify-center mt-10">
              <h1 className="text-4xl font-bold gray-900">Your public panel</h1>
              <div className="mt-5 gray-900">An interactive, public dashboard designed for your minecraft server,</div>
              <div className="gray-900">allowing everyone to observe informations, chat and so on!</div>
              <Button variant="outline" className="mt-5 bg-blue-300">Go to panel</Button>
            </div>
            {/*Cards*/}
            <div className="flex items-center justify-center mt-10 gap-4 items-stretch">
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
        {/* Footer */}
        <footer className = "shadow">
          <div className="flex flex-col mt-4 items-center justify-center bg-blue-300 h-40">
            <div className="flex gap-5 pt-4">
              <div className="group rounded-full bg-white p-2 hover:scale-105">
                <SiFacebook className="group-hover:text-blue-600 group-hover:scale-105 transition-colors duration-300"/>
              </div>
              <div className="group rounded-full bg-white p-2 hover:scale-105">
                <SiYoutube className="group-hover:text-red-600 group-hover:scale-105 transition-colors duration-300"/>
              </div>
              <div className="group rounded-full bg-white p-2 hover:scale-105">
                <SiDiscord className="group-hover:text-blue-600 group-hover:scale-105 transition-colors duration-300"/>
              </div>
              <div className="group rounded-full bg-white p-2 hover:scale-105">
                <SiInstagram className="group-hover:text-red-600 group-hover:scale-105 transition-colors duration-300"/>
              </div>
            </div>
            <div className="mt-2">
              JWebPanel - Interactive panel for minecraft servers!
            </div>
          </div>
          <div className="flex justify-center bg-gray-200 w-full mt-auto">Copyright © 2026 All rights reserved.</div>
        </footer>
      </div>
    );
  }