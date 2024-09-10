import React from 'react'
import './TransferPoint.scss'
import { useState,useEffect } from 'react'
import axios from 'axios'
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function TransferPoint() {
  const [userList,setUserList] = useState([])
  const [email,setEmail] = useState('Email user bạn muốn chuyển point')
  const [point,setPoint] = useState(0)
  const [myPoint,setMyPoint] = useState(0)
  const [userChoose,setUserChoose] = useState()
  useEffect(() => {
    axios.get(`http://localhost:8080/api/employees`)
    .then((res) => {
      console.log(res.data)
      setUserList(res.data)
    })
    .catch((err) => console.log(err))

    axios.get(`http://localhost:8080/api/rewards/${id}`)
    .then((res) => {
      setMyPoint(res.data)
    })
    .catch((err) => console.log(err))
  },[userList])
  const userInfo = JSON.parse(localStorage.getItem("userInfo"));
  const id = userInfo.employeeId;
  const chooseUser = (u) => {
    console.log(u)
    setUserChoose(u)
    setEmail(u.email)
  }

  const transerpoint = () => {
    console.log(id,userChoose.id,point)
    axios.post(`http://localhost:8080/api/employees/transfer`,{"fromId" : id,"toId" : userChoose.id,"amount" : point})
    .then((res) => {
      console.log(res.data)
      toast.success("Transfer point successful!");
    })
    .catch((err) =>   toast.error("An error occurred. Please try again."))
  }
  return (
    <div className='TransferPoint-container'>
      <ToastContainer />
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
      <div className='transferpoint'>
        <div className='transfer-infor'>
          <div>Email : </div>
          <input placeholder={email} className='transfer-input' disabled/>
        </div>
        <div className='transfer-infor'>
          <div>Số điểm : </div>
          <input placeholder={point} className='transfer-input' onChange={(e) => setPoint(e.target.value)}/>
        </div>
        <div> Số điểm hiện tại: {myPoint.totalPoints}</div>
       <div className='transfer-button'>
        <button className='button-approve' onClick={() => transerpoint()}> Chuyển point</button>
        <button className='button-cancel'> Huỷ</button>
       </div>
      </div>
    </div>
  )
}

export default TransferPoint