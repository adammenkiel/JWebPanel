import type React from "react";
import { Navigate } from "react-router-dom";

type ElementProps = {
    children : React.ReactNode;
}

export default function ProtectedRoute({children}: ElementProps) {
    const logged = localStorage.getItem("logged");
    if(logged == null) {
        return (
            <Navigate to="/"/>
        );
    }
    return ({children});
}