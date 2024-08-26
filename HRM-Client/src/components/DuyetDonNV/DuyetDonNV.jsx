// src/ContactForm.js
import React, { useState, useEffect } from 'react';
import './DuyetDonNV.scss'
function DuyetDonNV() {
    const [selectedRow, setSelectedRow] = useState(null);
    const [forms, setForms] = useState([]);

    
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


     



    const handleRowClick = (index) => {
      setSelectedRow(index);
    };
  
    return (
        <>

<table style={{ width: '100%', borderCollapse: 'collapse' }}>
        <thead>
          <tr>
            <th style={{ border: '1px solid black', padding: '10px' }}>Id</th>
            <th style={{ border: '1px solid black', padding: '10px' }}>Name</th>
            <th style={{ border: '1px solid black', padding: '10px' }}>Nghỉ từ ngày</th>
            <th style={{ border: '1px solid black', padding: '10px' }}>Ngày đi làm lại</th>
            <th style={{ border: '1px solid black', padding: '10px' }}>Loại Nghỉ</th>
            <th style={{ border: '1px solid black', padding: '10px' }}>Lý Do</th>
          </tr>
        </thead>
        <tbody>
          {forms.map((person, index) => (
            <tr
              key={person.id}
              onClick={() => handleRowClick(index)}
              style={{
                backgroundColor: selectedRow === index ? 'lightblue' : 'white',
                cursor: 'pointer',
              }}
            >
              <td style={{ border: '1px solid black', padding: '10px' }}>{person.id}</td>
              <td style={{ border: '1px solid black', padding: '10px' }}>{person.name}</td>
              <td style={{ border: '1px solid black', padding: '10px' }}>{person.dayRest}</td>
              <td style={{ border: '1px solid black', padding: '10px' }}>{person.dateBackToWork}</td>
              <td style={{ border: '1px solid black', padding: '10px' }}>{person.type}</td>
              <td style={{ border: '1px solid black', padding: '10px' }}>{person.reason}</td>
            </tr>
          ))}
        </tbody>
      </table>
      

      <div className="button-container">
        <button className='Duyet'>Duyệt</button>
        <button className='KoDuyet'>Không Duyệt</button>
        </div></>
      
    );
}



export default DuyetDonNV;
