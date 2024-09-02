import React from 'react'
import './404.scss'
import Header from "../../components/common/Header/Header.jsx";
import Footer from "../../components/common/Footer/Footer.jsx";


function Page404() {
  return (
    <div className='page404_container'>
      <Header/>
      <div className='page404_content'> 404 Not Found </div>
      <Footer/>
    </div>
  )
}

export default Page404