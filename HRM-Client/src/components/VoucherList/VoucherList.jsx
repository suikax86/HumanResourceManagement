import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './VoucherList.scss';

function VoucherList() {
  const [select, setSelect] = useState('All');
  const [vouchers, setVouchers] = useState([]);
  const [selectedVoucher, setSelectedVoucher] = useState(null);
  const [employee, setEmployee] = useState({ name: '', points: 0 });

  useEffect(() => {
    axios.get('http://localhost:8080/api/vouchers')
      .then(response => setVouchers(response.data))
      .catch(error => console.error('Error fetching vouchers:', error));
  }, []);

  useEffect(() => {
    const userInfo = JSON.parse(localStorage.getItem("userInfo"));
    const employeeId = userInfo.employeeId;

    axios.get(`http://localhost:8080/api/employees/${employeeId}`)
      .then(response => setEmployee(response.data))
      .catch(error => console.error('Error fetching employee info:', error));
  }, []);

  const chooseVoucherBrand = (brand) => {
    setSelect(brand);
  };

  const handleVoucherClick = (voucher) => {
    setSelectedVoucher(voucher);
  };

  const redeemVoucher = (voucherId) => {
    const userInfo = JSON.parse(localStorage.getItem("userInfo"));
    const employeeId = userInfo.employeeId;

    axios.post('http://localhost:8080/api/vouchers/redeem', { voucherId, employeeId })
      .then(response => {
        if (response.status === 200) {
          toast.success('Voucher redeemed successfully!');
          window.location.href = '/MyVoucher'; 
        }
      })
      .catch(error => {
        if (error.response && error.response.status === 400) {
          toast.error(`Error: ${error.response.data.message}`);
        } else {
          toast.error('An unexpected error occurred.');
        }
      });
  };

  return (
    <div className='VoucherList_container'>
      <div>
        <h2>Thông tin nhân viên</h2>
        <p>Tên: {employee.name}</p>
        <p>Số points: {employee.rewardPoints}</p>
      </div>
      
      <div className='Category'>
        {['All', ...new Set(vouchers.map(v => v.brand))].map((brand, id) => (
          <div className='Category_name' key={id} onClick={() => chooseVoucherBrand(brand)}>
            {brand}
          </div>
        ))}
      </div>
      <div className='Voucher'>
        {select === 'All' ? vouchers.map((v, id) => (
          <div className='Voucher_detail' key={id} onClick={() => handleVoucherClick(v)}>
            <div className='Voucher_brand'>{v.brand}</div>
            <div className='Voucher_name'>{v.name}</div>
            <div className='Voucher_description'>{v.description}</div>
            <div className='Voucher_HSD'>Hạn sử dụng: {v.dateEnd}</div>
            <div className='Voucher_point'>Số point đổi: {v.points}</div>
            {selectedVoucher && selectedVoucher.id === v.id && (
              <button onClick={() => redeemVoucher(v.id)}>Đổi</button>
            )}
          </div>
        )) : vouchers.filter(v => v.brand === select).map((v, id) => (
          <div className='Voucher_detail' key={id} onClick={() => handleVoucherClick(v)}>
            <div className='Voucher_brand'>{v.brand}</div>
            <div className='Voucher_name'>{v.name}</div>
            <div className='Voucher_description'>{v.description}</div>
            <div className='Voucher_HSD'>Hạn sử dụng: {v.dateEnd}</div>
            <div className='Voucher_point'>Số point đổi: {v.points}</div>
            {selectedVoucher && selectedVoucher.id === v.id && (
              <button onClick={() => redeemVoucher(v.id)}>Đổi</button>
            )}
          </div>
        ))}
      </div>
      <ToastContainer position="bottom-right"/>
    </div>
  );
}

export default VoucherList;