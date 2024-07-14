import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from './pages/login/login';
import Homepage from './pages/homepage/homepage';
import Myactivity from './pages/myactivity/myactivity';
import Myprofile from './pages/myprofile/myprofile';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
   <BrowserRouter>
      <Routes>
        <Route  path="/" element={<Login />} />
        <Route exac path="/homepage" element={<Homepage />} />
        <Route exac path="/myactivity" element={<Myactivity />} />
        <Route exac path="/myprofile" element={<Myprofile />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>,
)
