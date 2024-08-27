// src/ContactForm.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './DuyetDonNV.scss'
function DuyetDonNV() {
    const [selectedRow, setSelectedRow] = useState(null);
    const [forms, setForms] = useState([]);

    const [error, setError] = useState('');
    const [ResponseMessage, setResponseMessage ] = useState('');
    const [formData, setFormData] = useState({
      id: '',
      employeeId: '',
      name: '',
      phone: '',
      dayRest: '',
      dateBackToWork: '',
      type: '',
      formCondition:'',
      reason: ''
    });

  
    useEffect(() => {
        fetch('http://localhost:8081/api/forms')
          .then(response => {
            if (!response.ok) {
              throw new Error('Network response was not ok');
            }
            return response.json();
          })
          .then(data => {
            setForms(data);
            setLoading(false);
          })
          .catch(error => {
            setError(error);
            setLoading(false);
          });
      }, []);

 
     



    const handleRowClick = (index,form) => {
      setSelectedRow(index);
      setFormData({
        id: form.id,
        employeeId: form.employeeId,
        name: form.name,
        phone: form.phone,
        dayRest: form.dayRest,
        dateBackToWork: form.dateBackToWork,
        type: form.type,
        formCondition: form.formCondition,
        reason: form.reason
      });
      console.log('personID: ', formData);
      console.log('state Form:');
      
    };
    const DuyetDon = () =>{
     
      
      setFormData({
        ...formData,
        formCondition: 'Approved' // Replace 'newValue' with the new value for formCondition
      });
      console.log('test log: ',formData.id);


      axios.put(`http://localhost:8081/api/forms/${formData.id}`, {
        id: formData.id,
        employeeId: formData.employeeId,
        name: formData.name,
        phone: formData.phone,
        dayRest: formData.dayRest,
        dateBackToWork: formData.dateBackToWork,
        type: formData.type,
        formCondition: "Approved",
        reason: formData.reason
      })
        .then(response => {
          setResponseMessage('Form updated successfully!');
          setError('');
  
          // Optionally refresh the forms list
          setForms(forms.map(form => (form.id === formData.id ? formData : form)));
        })
        .catch(err => {
          setError('Error updating form.');
          setResponseMessage('');
        });
      
      
    }

    const KhongDuyetDon = () =>{
     
      
      setFormData({
        ...formData,
        formCondition: 'Denied' // Replace 'newValue' with the new value for formCondition
      });
      console.log('test log: ',formData.id);


      axios.put(`http://localhost:8081/api/forms/${formData.id}`, {
        id: formData.id,
        employeeId: formData.employeeId,
        name: formData.name,
        phone: formData.phone,
        dayRest: formData.dayRest,
        dateBackToWork: formData.dateBackToWork,
        type: formData.type,
        formCondition: "Denied",
        reason: formData.reason
      })
        .then(response => {
          setResponseMessage('Form updated successfully!');
          setError('');
  
          // Optionally refresh the forms list
          setForms(forms.map(form => (form.id === formData.id ? formData : form)));
        })
        .catch(err => {
          setError('Error updating form.');
          setResponseMessage('');
        });
      
      
    }


    return (
        <>

<table style={{ width: '100%', borderCollapse: 'collapse' }}>
        <thead>
          <tr>
            <th style={{ border: '1px solid black', padding: '10px' }}>Stt</th>
            <th style={{ border: '1px solid black', padding: '10px' }}>Id Nhân viên</th>
            <th style={{ border: '1px solid black', padding: '10px' }}>Name</th>
            <th style={{ border: '1px solid black', padding: '10px' }}>Nghỉ từ ngày</th>
            <th style={{ border: '1px solid black', padding: '10px' }}>Ngày đi làm lại</th>
            <th style={{ border: '1px solid black', padding: '10px' }}>Loại Nghỉ</th>
            <th style={{ border: '1px solid black', padding: '10px' }}>Lý Do</th>
            <th style={{ border: '1px solid black', padding: '10px' }}>tình trạng đơn</th>
          </tr>
        </thead>
        <tbody>
          {forms.map((person, index) => (
            <tr
              key={person.id}
              onClick={() => handleRowClick(index,person)}
              style={{
                backgroundColor: selectedRow === index ? 'lightblue' : 'white',
                cursor: 'pointer',
              }}
            >
              <td style={{ border: '1px solid black', padding: '10px'  }}>{person.id}</td>
              <td style={{ border: '1px solid black', padding: '10px'  }}>{person.employeeId}</td>
              <td style={{ border: '1px solid black', padding: '10px' }}>{person.name}</td>
              <td style={{ border: '1px solid black', padding: '10px' }}>{person.dayRest}</td>
              <td style={{ border: '1px solid black', padding: '10px' }}>{person.dateBackToWork}</td>
              <td style={{ border: '1px solid black', padding: '10px' }}>{person.type}</td>
              <td style={{ border: '1px solid black', padding: '10px' }}>{person.reason}</td>
              <td style={{ border: '1px solid black', padding: '10px', backgroundColor:
      person.formCondition === 'Pending' ? 'purple' :
      person.formCondition === 'Approved' ? 'green' :
      person.formCondition === 'Denied' ? 'red' : 'white' }}>{person.formCondition}</td>
            </tr>
          ))}
        </tbody>
      </table>
      

      <div className="button-container">
        <button onClick={()=>{DuyetDon()}} className='Duyet'>Duyệt</button>
        <button  onClick={()=>{KhongDuyetDon()}}   className='KoDuyet'>Từ Chối</button>
        </div></>
      
    );
}



export default DuyetDonNV;
