import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from './pages/login/login';
import Homepage from './pages/homepage/homepage';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
   <BrowserRouter>
      <Routes>
        <Route  path="/" element={<Login />} />
        <Route exac path="/homepage" element={<Homepage />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>,
)
