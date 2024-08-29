import React,{useState} from 'react'
import './VoucherList.scss'
import {brands} from '../../constrant'
import { voucher } from '../../constrant'

function VoucherList() {
  const [select,setSelect] = useState('All')
  const chooseVoucherBrand = (brand) => {
      setSelect(brand)

  }
  console.log(select)
  return (
    <div className='VoucherList_container'>
      <div className='Category'>
          {brands.map((brand,id) => {
            return <div className='Category_name' key={id} onClick={() => chooseVoucherBrand(brand)}>{brand}</div>
          })}
      </div> 
      <div className='Voucher'>
      {select === 'All' ? voucher.map((v,id) => {
            return <div className='Voucher_detail' key={id}>
              <div className='Voucher_brand'>{v.brand}</div>
              <div className='Voucher_HSD'>Hạn sử dụng: {v.HSD}</div>
              <div className='Voucher_point'>Số point đổi: {v.point}</div>
            </div>
          }) : voucher.filter((v) => v.brand === select
          ).map((v,id) => {
            return <div className='Voucher_detail' key={id}>
              <div className='Voucher_brand'>{v.brand}</div>
              <div className='Voucher_HSD'>Hạn sử dụng: {v.HSD}</div>
              <div className='Voucher_point'>Số point đổi: {v.point}</div>
            </div>
          })}
      </div>
    </div>
  )
}

export default VoucherList