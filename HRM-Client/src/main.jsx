import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import HomePage from './pages/HomePage/HomePage.jsx';
import Login from './pages/Login/Login.jsx'
import MyProfile from './pages/MyProfile/MyProfile.jsx';
import DonXinNghi from './pages/DonXinNghi/Don.jsx';
import WorkFromHome from './pages/WorkFromHome/WorkHome.jsx';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
   <BrowserRouter>
      <Routes>
        <Route  path="/" element={<HomePage />} />
        <Route exac path="/Login" element={<Login />} />
        <Route exac path="/HomePage" element={<HomePage />} />
        <Route exac path="/MyProfile" element={<MyProfile />} />
        <Route exac path="/DonXinNghi" element={<DonXinNghi />} />
        <Route exac path="/WorkFromHome" element={<WorkFromHome />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>,
)
