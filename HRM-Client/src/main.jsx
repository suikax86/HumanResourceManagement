import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import HomePage from './pages/HomePage/HomePage.jsx';
import Login from './pages/Login/Login.jsx'
import MyProfile from './pages/MyProfile/MyProfile.jsx';
import DonXinNghi from './pages/DonXinNghi/Don.jsx';
import DuyetDon from './pages/DuyetDon/DuyetDon.jsx';
import HistoryApplication from './pages/HistoryApplication/History.jsx';
import UpdateTime from './pages/UpdateTime/UpdateTime.jsx';
import ShowSheet from './pages/ShowSheet/ShowSheet.jsx';

ReactDOM.createRoot(document.getElementById('root')).render(
  
  <React.StrictMode>
   <BrowserRouter>
      <Routes>
        <Route  path="/" element={<HomePage />} />
        <Route exac path="/Login" element={<Login />} />
        <Route exac path="/HomePage" element={<HomePage />} />
        <Route exac path="/MyProfile" element={<MyProfile />} />
        <Route exac path="/DonXinNghi" element={<DonXinNghi />} />
        <Route exac path="/DonXinNghi" element={<DonXinNghi />} />
        <Route exac path="/HistoryApplication" element={<HistoryApplication />} />
        <Route exac path="/DuyetDon" element={<DuyetDon />} />
        <Route exac path="/TimeSheet" element={<UpdateTime />} />
        <Route exac path="/ShowTimeSheet" element={<ShowSheet />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>,
)
