import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import HomePage from './pages/HomePage/HomePage';
import MyProfile from './pages/MyProfile/MyProfile';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
   <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/HomePage" element={<HomePage />} />
        <Route path="/MyProfile" element={<MyProfile />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>,
)
