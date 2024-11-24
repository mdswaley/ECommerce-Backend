import React, { useState, useEffect } from "react";
import apiClient from "./apiClient"; 

const NewArrivals = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await apiClient.get("/product/getAll"); // API call using interceptor
        setProducts(response.data);
        setLoading(false);
      } catch (err) {
        setError("Failed to load products");
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  const addToCart = async (productId) => {
    const userId = Number(localStorage.getItem("user_id"));
    if (!userId || isNaN(userId)) {
      alert("Invalid user ID. Please log in again.");
      return;
    }

    try {
      const response = await apiClient.post(`/cart/${userId}/UserAddProduct/${productId}`);
      if (response.status === 201) {
        alert("Product added to cart successfully!");
      } else {
        alert("Failed to add product to cart. Please try again.");
      }
    } catch (error) {
      console.error("Error adding product to cart:", error.response ? error.response.data : error.message);
      alert("An error occurred while adding the product to the cart.");
    }
  };

  if (loading) {
    return <div className="text-center text-gray-500">Loading products...</div>;
  }

  if (error) {
    return <div className="text-center text-red-500">{error}</div>;
  }

  return (
    <section className="py-10 px-4">
      <div className="container mx-auto">
        {/* Title */}
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-3xl font-bold text-gray-800">New Arrivals</h2>
        </div>

        {/* Product Cards */}
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
          {products.map((product) => (
            <div
              key={product.id}
              className="bg-white shadow-md rounded-md overflow-hidden p-4"
            >
              {/* Product Image */}
              <div className="relative">
                {product.name && (
                  <span className="absolute top-2 left-2 bg-black text-white text-xs uppercase px-2 py-1 rounded">
                    {product.review} ⭐
                  </span>
                )}

                {product.availability && (
                  <span
                    className={`absolute top-2 right-2 text-white text-xs px-2 py-1 rounded ${
                      product.availability === "OUT_OF_STOCK"
                        ? "bg-yellow-500"
                        : product.availability === "IN_STOCK"
                        ? "bg-green-500"
                        : product.availability === "DISCONTINUED"
                        ? "bg-red-500"
                        : "bg-res-500"
                    }`}
                  >
                    {product.availability}
                  </span>
                )}

                <img
                  src={product.url || "https://via.placeholder.com/150"} // Fallback if no image
                  alt={product.name}
                  className="w-full h-48 object-cover"
                />
              </div>

              {/* Product Details */}
              <div className="mt-4">
                <h3 className="text-gray-800 font-semibold">{product.name}</h3>
                <div className="flex items-center space-x-2 text-sm">
                  <span className="text-gray-800">{`Rs:${product.price}`}</span>
                  {product.description && (
                    <span className="text-gray-400">
                      {product.description}
                    </span>
                  )}
                </div>
                {/* Add to Cart Button */}
                <button
                  onClick={() => addToCart(product.id)} // Call the addToCart function with product ID
                  className="mt-4 bg-black text-white py-2 px-4 rounded w-full"
                >
                  Add to cart
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};

export default NewArrivals;
