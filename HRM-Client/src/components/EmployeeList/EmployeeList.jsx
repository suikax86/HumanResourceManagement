import React from 'react'
import './EmployeeList.scss'
import { useState,useEffect } from 'react'
import axios from 'axios'

function EmployeeList() {
  const [userList,setUserList] = useState([])
  const [Uid, setUid] = useState()
  const [Uname, setUname] = useState('')
  const [Uemail, setUemail] = useState('')
  const [Uposition, setUposition] = useState('')
  const [Ucancuoc, setUcancuoc] = useState('')
  const [Umst, setUmst] = useState('')
  const [Usdt, setUsdt] = useState('')
  const [Udc, setUdc] = useState('')
  const [Utnh, setUtnh] = useState('')
  const [Usnh, setUsnh] = useState('')
  const [Udt, setUdt] = useState('')
  useEffect(() => {
    axios.get(`http://localhost:8080/api/employees`)
    .then((res) => {
      setUserList(res.data)
    })
    .catch((err) => console.log(err))
  },[userList])

  const userInfo = JSON.parse(localStorage.getItem("userInfo"));
  const id = userInfo.employeeId;

  const chooseUser = (u) => {
    console.log(u)
    setUid(u.id)
    setUname(u.name)
    setUemail(u.email)
    setUposition(u.role)
    setUcancuoc(u.idNumber)
    setUmst(u.taxNumber)
    setUsdt(u.phonenumber)
    setUdc(u.address)
    setUtnh(u.bankName)
    setUsnh(u.bankNumber)
    setUdt(u.rewardPoints)
  }
  return (
    <div className='EmployeeList_container'>
      <div className='user-search-list'>
        <div className='user-list-title'>Danh sách nhân viên</div>
        <div className='user-list'>
          {
            userList.map((user,key) => {
              return id !== user.id ? <div className='user-list-container' key={key} onClick={() => chooseUser(user)}>
                <div className='user-list-detail'>Email : {user.email}</div> 
                <div className='user-list-detail'>Tên : {user.name}</div> 
              </div> : <></>
            })
          }
        </div>
      </div>
      <div className='User_information'>
        <div className='User_information_title'> Danh sách nhân viên</div>
        <div className='User_information_infor'>
          <div className='User_information_detail'>
            <div className='User_information_detail_tilte'>Id : </div>
            <div className='User_information_detail_infor'>{Uid}</div>
          </div>
          <div className='User_information_detail'>
            <div className='User_information_detail_tilte'>Tên : </div>
            <div className='User_information_detail_infor'>{Uname}</div>
          </div>
          <div className='User_information_detail'>
            <div className='User_information_detail_tilte'>Email : </div>
            <div className='User_information_detail_infor'>{Uemail}</div>
          </div>
          <div className='User_information_detail'>
            <div className='User_information_detail_tilte'>Role : </div>
            <div className='User_information_detail_infor'>{Uposition}</div>
          </div>
          <div className='User_information_detail'>
            <div className='User_information_detail_tilte'>Căn cước : </div>
            <div className='User_information_detail_infor'>{Ucancuoc}</div>
          </div>
          <div className='User_information_detail'>
            <div className='User_information_detail_tilte'>Mã số thuế : </div>
            <div className='User_information_detail_infor'>{Umst}</div>
          </div>
          <div className='User_information_detail'>
            <div className='User_information_detail_tilte'>Số điện thoại : </div>
            <div className='User_information_detail_infor'>{Usdt}</div>
          </div>
          <div className='User_information_detail'>
            <div className='User_information_detail_tilte'>Địa chỉ : </div>
            <div className='User_information_detail_infor'>{Udc}</div>
          </div>
          <div className='User_information_detail'>
            <div className='User_information_detail_tilte'>Tên ngân hàng : </div>
            <div className='User_information_detail_infor'>{Utnh}</div>
          </div>
          <div className='User_information_detail'>
            <div className='User_information_detail_tilte'>Số tài khoản ngân hàng : </div>
            <div className='User_information_detail_infor'>{Usnh}</div>
          </div>
          <div className='User_information_detail'>
            <div className='User_information_detail_tilte'>Điểm thưởng : </div>
            <div className='User_information_detail_infor'>{Udt}</div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default EmployeeList