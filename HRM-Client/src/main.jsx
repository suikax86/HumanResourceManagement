import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import HomePage from './pages/HomePage/HomePage.jsx';
import MyProfile from './pages/MyProfile/MyProfile.jsx';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
   <BrowserRouter>
      <Routes>
        <Route  path="/" element={<HomePage />} />
        <Route exac path="/HomePage" element={<HomePage />} />
        <Route exac path="/MyProfile" element={<MyProfile />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>,
)
