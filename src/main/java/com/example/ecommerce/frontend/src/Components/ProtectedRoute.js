import React from "react";
import { Navigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

const ProtectedRoute = ({ children }) => {
  const accessToken = localStorage.getItem("accessToken");

  if (!accessToken) {
    // Redirect to login if no token
    return <Navigate to="/" />;
  }

  try {
    const decodedToken = jwtDecode(accessToken);
    const currentTime = Date.now() / 1000;

    // Check if the token is expired
    if (decodedToken.exp < currentTime) {
      localStorage.clear();
      return <Navigate to="/" />;
    }
  } catch (error) {
    console.error("Invalid Token:", error);
    localStorage.clear();
    return <Navigate to="/" />;
  }

  // If token is valid, render the child components
  return children;
};

export default ProtectedRoute;
