import React from 'react'
import './MyPoint.scss'
import Header from "../../components/common/Header/Header.jsx";
import Footer from "../../components/common/Footer/Footer.jsx";
import Mypoint from '../../components/MyPoint/MyPoint.jsx';

function MyPoint() {
    return (
        <div className='MyPoint'>
            <Header/>
            <Mypoint/>
            <Footer/>
        </div>
    )
}

export default MyPoint