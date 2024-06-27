import React from 'react'
import './homepage.scss'
import Header from '../../components/common/header/header'
import Home from '../../components/home/home'
import Footer from '../../components/common/footer/footer'

function Homepage() {
  return (
    <div className='homepage_container'>
      <Header/>
      <Home/>
      <Footer/>
    </div>
  )
}

export default Homepage