import React from 'react'
import './VoucherPage.scss'
import Header from '../../components/common/Header/Header'
import Footer from '../../components/common/Footer/Footer'
import VoucherList from '../../components/VoucherList/VoucherList'

function VoucherPage() {
  return (
    <div className='VoucherPage_Container'>
      <Header/>
      <VoucherList/>
      <Footer/>
    </div>
  )
}

export default VoucherPage