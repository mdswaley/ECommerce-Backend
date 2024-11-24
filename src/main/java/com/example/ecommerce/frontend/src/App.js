import React from 'react';
import { Router, Routes, Route } from 'react-router-dom';
import AuthPage from './Components/AuthPage'; // Import your AuthPage component
import HomePage from './Components/HomePage'; // Import your HomePage component
import NavPro from './Components/NavPro';
import ProtectedRoute from './Components/ProtectedRoute';
import AddProduct from './Components/AddProduct';
import Navbar from './Components/Navbar';
import Cart from './Components/Cart';
import UpdateProduct from './Components/UpdateProduct';

function App() {
  return (
 
      <Routes>
      
        <Route path="/" element={<AuthPage />} />



        {/* Protected Routes */}
        <Route
          path="/navpro"
          element={
            <ProtectedRoute>
              <NavPro />
            </ProtectedRoute>
          }
        />

      <Route
          path="/addProduct"
          element={
            <ProtectedRoute>
              <AddProduct />
            </ProtectedRoute>
          }
        />

      <Route
          path="/updateProduct"
          element={
            <ProtectedRoute>
              <UpdateProduct />
            </ProtectedRoute>
          }
        />

      <Route
          path="/cart"
          element={
            <ProtectedRoute>
              <Cart />
            </ProtectedRoute>
          }
        />
      </Routes>

  );
}

export default App;
