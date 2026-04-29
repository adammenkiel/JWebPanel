
import { 
    SiDiscord,
    SiFacebook,
    SiInstagram,
    SiYoutube 
} from "@icons-pack/react-simple-icons";


export default function Footer() {
    return (
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
    );
}