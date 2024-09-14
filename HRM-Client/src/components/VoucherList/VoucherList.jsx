import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './VoucherList.scss';

function VoucherList() {
  const [vouchers, setVouchers] = useState([]);
  const [selectedVoucher, setSelectedVoucher] = useState(null);
  const [employee, setEmployee] = useState({ name: '', points: 0 });

  useEffect(() => {
    axios.get('http://localhost:8080/api/vouchery/campaign/4')
      .then(response => {
        if (response.data && response.data.children) {
          setVouchers(response.data.children);
        }
      })
      .catch(error => console.error('Error fetching vouchers:', error));
  }, []);

  useEffect(() => {
    const userInfo = JSON.parse(localStorage.getItem("userInfo"));
    const employeeId = userInfo.employeeId;

    axios.get(`http://localhost:8080/api/employees/${employeeId}`)
      .then(response => setEmployee(response.data))
      .catch(error => console.error('Error fetching employee info:', error));
  }, []);

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
        <p>Tên nhân viên: {employee.name}</p>
        <p>Số points hiện tại: {employee.rewardPoints}</p>
      </div>
      
      <div className='Voucher'>
        {vouchers.map((v, id) => (
          <div className='Voucher_detail' key={id} onClick={() => handleVoucherClick(v)}>
            <div className='Voucher_name'>{v.name}</div>
            <div className='Voucher_description'>{v.description}</div>
            <div className='Voucher_HSD'>Hạn sử dụng: {new Date(v.end_at).toLocaleDateString()}</div>
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