import React from 'react'
import Header from "../../components/common/Header/Header.jsx";
import Footer from "../../components/common/Footer/Footer.jsx";
import CheckInCheckOut from '../../components/CheckInCheckOut/CheckInCheckOut.jsx';
import './CheckInCheckOut.scss'

function CheckInCheckOutPage() {
  return (
    <div className='checkincheckoutpage-container'>
      <Header/>
      <CheckInCheckOut/>
      <Footer/>
    </div>
  )
}

export default CheckInCheckOutPage