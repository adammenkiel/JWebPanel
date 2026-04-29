import { BrowserRouter, Route, Routes } from "react-router-dom";
import MainPage from "./pages/main-page";
import NavigateBar from "@/components/elem/navigate-bar";
import Footer from "./components/elem/footer";
import NotFound from "./pages/not-found";

export default function App() {
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
      </BrowserRouter>
  );
}