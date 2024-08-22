import React from 'react'
import './HomePage.scss'
import Header from '../../components/common/Header/Header.js'
import Home from '../../components/Home/Home.js'
import Footer from '../../components/common/Footer/Footer.js'

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