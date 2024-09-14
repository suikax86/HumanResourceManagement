import React from 'react'
import './EmployeeList.scss'
import Header from "../../components/common/Header/Header.jsx";
import Footer from "../../components/common/Footer/Footer.jsx";
import EmployeeListt from '../../components/EmployeeList/EmployeeList.jsx';

function EmployeeList() {
  return (
    <div className='EmployeeList'>
      <Header/>
      <EmployeeListt/>
      <Footer/>
    </div>
  )
}

export default EmployeeList