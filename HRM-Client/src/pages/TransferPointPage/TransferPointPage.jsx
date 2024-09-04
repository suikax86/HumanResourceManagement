import React from 'react'
import './TransferPointPage.scss'
import Header from '../../components/common/Header/Header.jsx'
import Footer from '../../components/common/Footer/Footer.jsx'
import TransferPoint from '../../components/TransferPoint/TransferPoint.jsx'

function TransferpointPage() {
  return (
    <div className='transferpointPage-container'>
      <Header/>
      <TransferPoint/>
      <Footer/>
    </div>
  )
}

export default TransferpointPage