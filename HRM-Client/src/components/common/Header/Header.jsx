import React from 'react'
import './header.scss'


function header() {
  return (
    <div className='header_container'>
      <div className='header_container_company_name'>Công ty xyz</div>
      <div className='header_container_nav'>
        <div className='header_container_nav_work'>Trang chủ</div>
        <div className='header_container_nav_notwork'>Hoạt động của tôi</div>
        <div className='header_container_nav_notwork'>Hồ sơ của tôi</div>
      </div>
      <button className='header_container_button'>Log in</button>
    </div>
  )
}

export default header