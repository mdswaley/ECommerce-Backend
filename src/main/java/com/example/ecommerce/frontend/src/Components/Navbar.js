import React from "react";


const Navbar = () => {

  return (
    <header className="bg-white shadow-md">
      <div className="container mx-auto px-4 flex justify-between items-center py-4">
      
        <div className="text-2xl font-bold text-gray-800"/>

        <nav>
          <ul className="flex space-x-6 text-gray-600">
            <li>
              <a
                href="/"
                className="hover:text-gray-800 transition duration-300"
              >
                Logout
              </a>
            </li>
            <li>
              <a
                href="/cart"
                className="hover:text-gray-800 transition duration-300"
              >
                Cart
              </a>
            </li>
            <li>
              <a
                href="/addProduct"
                className="hover:text-gray-800 transition duration-300"
              >
                ADD Product
              </a>
            </li>

            <li>
              <a
                href="/updateProduct"
                className="hover:text-gray-800 transition duration-300"
              >
                Update Product
              </a>
            </li>

            <li>
              <a
                href="/contact"
                className="hover:text-gray-800 transition duration-300"
              >
                Contact Us
              </a>
            </li>
          </ul>
        </nav>

        <div className="flex items-center space-x-4 text-gray-600">
          <i className="fa fa-search hover:text-gray-800 transition duration-300 cursor-pointer"></i>
          <i className="fa fa-shopping-bag hover:text-gray-800 transition duration-300 cursor-pointer"></i>
          <i className="fa fa-user hover:text-gray-800 transition duration-300 cursor-pointer"></i>
        </div>
      </div>
    </header>
  );
};

export default Navbar;
