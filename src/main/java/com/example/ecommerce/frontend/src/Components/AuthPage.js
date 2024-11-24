import React, { useState } from "react";
import axios from "axios";
import chair from "../Images/Chair_signup.png";
import { jwtDecode } from "jwt-decode";

const AuthPage = () => {
  const [isSignIn, setIsSignIn] = useState(true); // Toggle between Sign In and Sign Up
  const [formData, setFormData] = useState({
    name: "",
    userName: "",
    email: "",
    password: "",
  });
  const [error, setError] = useState(null);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSignUp = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/auth/signup", {
        name: formData.name,
        userName: formData.userName,
        email: formData.email,
        password: formData.password,
      });
      alert("Signup successful! Please sign in.");
      setIsSignIn(true);
    } catch (err) {
      setError(err.response?.data?.message || "Signup failed. Please try again.");
    }
  };

  const handleSignIn = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/auth/login", {
        email: formData.email,
        password: formData.password,
      });
  
      // Store tokens in localStorage
      const accessToken = response.data.accessToken;
      const refreshToken = response.data.refreshToken;
  
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);
  
      // Decode the accessToken to extract user_id
      const decodedToken = jwtDecode(accessToken);
      const userId = decodedToken.sub; // Adjust key based on your JWT payload structure
  
      if (!userId) {
        throw new Error("Failed to extract user_id from token.");
      }
  
      localStorage.setItem("user_id", userId);
  
      // Redirect to the home page
      alert("Sign in successful!");
      window.location.href = "/navpro"; // Replace with your home route
    } catch (err) {
      console.error("Sign in error:", err);
      setError(err.response?.data?.message || "Sign in failed. Please try again.");
    }
  };
  return (
    <div className="flex h-screen">
      {/* Left Section */}
      <div className="w-1/2 bg-gray-100 flex justify-center items-center">
        <img
          src={chair}
          alt="Decorative"
          className="w-3/4 rounded-lg shadow-lg"
        />
      </div>

      {/* Right Section */}
      <div className="w-1/2 flex flex-col justify-center px-12">
        <h1 className="text-3xl font-bold mb-6">
          {isSignIn ? "Sign In" : "Sign Up"}
        </h1>
        {error && <p className="text-red-500 mb-4">{error}</p>}
        <p className="mb-4">
          {isSignIn
            ? `Don't have an account yet? `
            : `Already have an account? `}
          <button
            onClick={() => {
              setIsSignIn(!isSignIn);
              setError(null);
            }}
            className="text-green-500 underline"
          >
            {isSignIn ? "Sign Up" : "Sign In"}
          </button>
        </p>
        {isSignIn ? (
          /* Sign In Form */
          <form onSubmit={handleSignIn}>
            <div className="mb-4">
              <label className="block text-sm font-medium mb-1">
                Email
              </label>
              <input
                type="text"
                name="email"
                value={formData.email}
                onChange={handleInputChange}
                className="w-full border border-gray-300 rounded-lg p-2"
                placeholder="Enter your email"
              />
            </div>
            <div className="mb-4">
              <label className="block text-sm font-medium mb-1">Password</label>
              <input
                type="password"
                name="password"
                value={formData.password}
                onChange={handleInputChange}
                className="w-full border border-gray-300 rounded-lg p-2"
                placeholder="Enter your password"
              />
            </div>
            <button className="bg-black text-white w-full py-2 rounded-lg">
              Sign In
            </button>
          </form>
        ) : (
          /* Sign Up Form */
          <form onSubmit={handleSignUp}>
            <div className="mb-4">
              <label className="block text-sm font-medium mb-1">Name</label>
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleInputChange}
                className="w-full border border-gray-300 rounded-lg p-2"
                placeholder="Enter your name"
              />
            </div>
            <div className="mb-4">
              <label className="block text-sm font-medium mb-1">Username</label>
              <input
                type="text"
                name="userName"
                value={formData.userName}
                onChange={handleInputChange}
                className="w-full border border-gray-300 rounded-lg p-2"
                placeholder="Enter your username"
              />
            </div>
            <div className="mb-4">
              <label className="block text-sm font-medium mb-1">Email</label>
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleInputChange}
                className="w-full border border-gray-300 rounded-lg p-2"
                placeholder="Enter your email"
              />
            </div>
            <div className="mb-4">
              <label className="block text-sm font-medium mb-1">Password</label>
              <input
                type="password"
                name="password"
                value={formData.password}
                onChange={handleInputChange}
                className="w-full border border-gray-300 rounded-lg p-2"
                placeholder="Enter your password"
              />
            </div>
            <button className="bg-black text-white w-full py-2 rounded-lg">
              Sign Up
            </button>
          </form>
        )}
      </div>
    </div>
  );
};

export default AuthPage;
