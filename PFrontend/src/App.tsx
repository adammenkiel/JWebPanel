
//import { Link } from "react-router-dom";
//import { Avatar, AvatarFallback, AvatarImage } from "./components/ui/avatar";

import { BrowserRouter, Route, Routes } from "react-router-dom";
import MainPage from "./pages/main-page";


export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainPage />}/>
      </Routes>
    </BrowserRouter>
  );
}