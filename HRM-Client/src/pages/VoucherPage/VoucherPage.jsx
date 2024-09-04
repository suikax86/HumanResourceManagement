import React from 'react'
import Header from '../../components/common/Header/Header'
import Footer from '../../components/common/Footer/Footer'
import VoucherList from '../../components/VoucherList/VoucherList'

function VoucherPage() {
  return (
    <div className="my-profile-container">
      <Header />
      <div className="content">
        <VoucherList />
      </div>
      <Footer />
    </div>
  )
}

export default VoucherPage