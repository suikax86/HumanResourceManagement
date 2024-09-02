import React from 'react'
import './403.scss'
import Header from "../../components/common/Header/Header.jsx";
import Footer from "../../components/common/Footer/Footer.jsx";


function Page403() {
  return (
    <div className='page403_container'>
      <Header/>
      <div className='page403_content'> 403 Forbiden error </div>
      <Footer/>
    </div>
  )
}

export default Page403