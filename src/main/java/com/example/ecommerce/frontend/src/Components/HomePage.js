import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const HomePage = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    if (!token) {
      alert("You are not logged in!");
      navigate("/");
    }
  }, [navigate]);

  return <h1>Welcome to the Home Page!</h1>;
};

export default HomePage;
