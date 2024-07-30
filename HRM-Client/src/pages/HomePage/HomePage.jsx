import React from 'react'
import './HomePage.scss'
import Header from '../../components/common/Header/Header.jsx'
import Home from '../../components/Home/Home.jsx'
import Footer from '../../components/common/Footer/Footer.jsx'

function HomePage() {
  return (
    <div className='homepage_container'>
      <Header/>
      <Home/>
      <Footer/>
    </div>
  )
}

export default HomePage