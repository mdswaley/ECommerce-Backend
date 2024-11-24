import React, { useState, useEffect } from "react";
import axios from "axios";

const Cart = () => {
  const [cartProducts, setCartProducts] = useState([]);
  const [error, setError] = useState(null);

  const getUserIdFromToken = (token) => {
    if (!token) return null;
    try {
      const payload = JSON.parse(atob(token.split(".")[1])); // Decode JWT
      return payload.sub; // Assuming user ID is stored in the `sub` field
    } catch (e) {
      console.error("Invalid token:", e);
      return null;
    }
  };

  useEffect(() => {
    const fetchCartProducts = async () => {
      const token = localStorage.getItem("accessToken");
      if (!token) {
        setError("You are not logged in.");
        return;
      }

      const userId = getUserIdFromToken(token);
      if (!userId) {
        setError("User ID not found in the token.");
        return;
      }

      try {
        const response = await axios.get(
          `http://localhost:8080/auth/user-products/${userId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`, // Include token in Authorization header
            },
          }
        );

        if (response.status === 200) {
          console.log("API Response:", response.data);
          setCartProducts(response.data.products || response.data); // Adjust based on response format
        }
      } catch (err) {
        setError("Failed to fetch cart products. Please try again.");
        console.error("Error fetching cart products:", err);
      }
    };

    fetchCartProducts(); // Fetch data when the component mounts
  }, []);

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="max-w-4xl mx-auto bg-white shadow-lg rounded-lg p-6">
        <h2 className="text-2xl font-bold text-gray-800 mb-4">Your Cart</h2>

        {error && (
          <div className="text-red-500 bg-red-100 p-4 rounded mb-4">
            {error}
          </div>
        )}

        <div>
          {cartProducts.length > 0 ? (
            <div className="space-y-4">
              {cartProducts.map((product) => (
                <div
                  key={product.id}
                  className="flex items-center justify-between border-b pb-4"
                >
                  <div>
                    <p className="font-semibold text-lg text-gray-800">
                      {product.name}
                    </p>
                    <p className="text-sm text-gray-500">
                        Policy: {Array.isArray(product.policy) 
                        ? product.policy.join(', ') // If it's an array, join with commas
                        : product.policy} {/* If it's already a string */}
                    </p>
                  </div>
                  <p className="text-lg font-medium text-green-600">
                    ₹{product.price}
                  </p>
                </div>
              ))}

              {/* Total Price */}
              <div className="text-right pt-4 border-t">
                <p className="text-lg font-bold text-gray-800">
                  Total: ₹
                  {cartProducts.reduce(
                    (total, product) => total + product.price ,
                    0
                  )}
                </p>
              </div>
            </div>
          ) : (
            <p className="text-gray-500 text-center">
              Your cart is currently empty.
            </p>
          )}
        </div>
      </div>
    </div>
  );
};

export default Cart;
