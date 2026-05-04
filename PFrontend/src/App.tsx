import { BrowserRouter, Route, Routes } from "react-router-dom";
import MainPage from "./pages/main-page";
import NavigateBar from "@/components/elem/navigate-bar";
import Footer from "./components/elem/footer";
import NotFound from "./pages/not-found";
import { useAuthContext } from "./components/elem/auth/auth-context";
import RegisterContainer from "./components/elem/auth/register-container";
import LoginContainer from "./components/elem/auth/login-container";
//import { createContext } from "react";


export default function App() {
  
  const { view } = useAuthContext();
  return (
      <BrowserRouter>
        <div className="bg-gray-100">
          <NavigateBar />
          <Routes>
            <Route path="/" element={<MainPage />}/>
            <Route path="*" element={<NotFound />} />
          </Routes>
          <Footer />
        </div>
        {view === "register" && (<RegisterContainer />)}
        {view === "login" && (<LoginContainer />)}
      </BrowserRouter>
  );
}