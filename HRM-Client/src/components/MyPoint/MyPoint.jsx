import React from 'react'
import axios from 'axios'
import { useState,useEffect } from 'react'
import './MyPoint.scss'

function MyPoint() {
  const [mypoint, setMypoint] = useState()
  const [his, setHis] = useState()
  const userInfo = JSON.parse(localStorage.getItem("userInfo"));
  const id = userInfo.employeeId;
  useEffect(() => {
    axios.get(`http://localhost:8080/api/rewards/${id}`)
    .then((res) => {
      console.log(res.data)
      setHis(res.data)
      setMypoint(res.data.totalPoints)
    })
    .catch((err) => console.log(err))

  })
  return (
    <div className='MyPoint_container'>
      <div className='mypoint'>
        <p style={{fontWeight:'bold'}}>Điểm thưởng của tôi</p> : {mypoint} point
      </div>
      <div className='history'>
        <div className='history_title'> Lịch sử chuyển point</div>
        <div className='history_detail'>
          {his && his.pointsHistory.map((h,id) => {
            return <div className='point_history'>
              {h.transactionType === "SUBTRACT" ? <div className='point_history_dtail'>Phương thức: Chuyển</div> : <div className='point_history_dtail'>Phương Thức: Nhận</div>}
              {h.transactionType === "SUBTRACT" ? <div className='point_history_dtail'>Tới id: {h.senderId}</div> : <div className='point_history_dtail'>Từ id: {h.senderId} </div>}
              <div className='point_history_dtail'>Số lượng : {h.points}</div>
              <div className='point_history_dtail'>{h.description}</div>
              <div className='point_history_dtail'>Thời gian : {h.timeStamp}</div>
            </div>
          })}
        </div>
      </div>
    </div>
  )
}

export default MyPoint