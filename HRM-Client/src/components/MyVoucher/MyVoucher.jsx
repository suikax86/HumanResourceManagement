import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './MyVoucher.scss';

function MyVoucher() {
  const [redeemedVouchers, setRedeemedVouchers] = useState([]);

  useEffect(() => {
    const userInfo = JSON.parse(localStorage.getItem("userInfo"));
    const employeeId = userInfo.employeeId;

    axios.get(`http://localhost:8080/api/rewards/${employeeId}/redeemed-vouchers`)
      .then(response => setRedeemedVouchers(response.data))
      .catch(error => console.error('Error fetching redeemed vouchers:', error));
  }, []);

  return (
    <div className='MyVoucher_container'>
      <h2>Voucher của tôi</h2>
      <div className='Voucher_list'>
        {redeemedVouchers.map((voucher, index) => (
          <div className='Voucher_item' key={index}>
            <h3>{voucher.campaignName}</h3>
            <p> Mô tả: {voucher.campaignDescription}</p>
            <p>Ngày hết hạn: {new Date(voucher.endAt).toLocaleDateString()}</p>
            <p>Code: {voucher.code}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default MyVoucher;