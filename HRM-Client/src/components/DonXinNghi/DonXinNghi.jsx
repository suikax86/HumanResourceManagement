import React, { useState } from 'react';
import axios from 'axios';
import './DonXinNghi.scss';
function DonXinNghi() {
  const [formData, setFormData] = useState({
    employeeId: '',
    name: '',
    phone: '',
    dayRest: '',
    dateBackToWork: '',
    type: '',
    reason: ''
  });

  const [selectedOption, setSelectedOption] = useState('');

  const handleTypeChange = (event) => {
    setSelectedOption(event.target.value);
    console.log('Selected option:', event.target.value);
  };

  const [responseMessage, setResponseMessage] = useState('');
  const [error, setError] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    axios.post('http://localhost:8081/api/forms', formData)
      .then(response => {
        setResponseMessage('Form submitted successfully!');
        setError('');
        // Optionally reset the form
        setFormData({
          employeeId: '',
          name: '',
          phone: '',
          dayRest: '',
          dateBackToWork: '',
          type: '',
          reason: ''
        });
      })
      .catch(err => {
        setError('Error submitting form.');
        setResponseMessage('');
      });
  };

  return (
    <div>
      <h1>Submit a Form</h1>
      <form onSubmit={handleSubmit}>
        <label>
          Employee ID:
          <input
            type="number"
            name="employeeId"
            value={formData.employeeId}
            onChange={handleChange}
            required
          />
        </label>
        <br />
        <label>
          Name:
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
          />
        </label>
        <br />
        <label>
          Phone:
          <input
            type="text"
            name="phone"
            value={formData.phone}
            onChange={handleChange}
            required
          />
        </label>
        <br />
        <label>
          Day Rest:
          <input
            type="date"
            name="dayRest"
            value={formData.dayRest}
            onChange={handleChange}
            required
          />
        </label>
        <br />
        <label>
          Date Back to Work:
          <input
            type="date"
            name="dateBackToWork"
            value={formData.dateBackToWork}
            onChange={handleChange}
            required
          />
        </label>
        <br />
        <label>
        <select
    name="type"
    value={formData.type}
    onChange={handleChange}
    required
  >
    <option value="" disabled>Type of rest</option>
    <option value="WFH">WFH</option>
    <option value="Nghi Phep">Nghỉ phép</option>
    <option value="Khac">Lý do riêng</option>
  </select>
        </label>
        

        
       


{/* 
<div>
      <h1>Choose an Option</h1>
      <select value={formData.type} onChange={handleChange}>
        <option value="" disabled>Select Yes or No</option>
        <option value="WFH">WFH</option>
        <option value="Nghi phep">Nghi Phep</option>
      </select>
      {formData.type && <p>You selected: {formData.type}</p>}
    </div> */}





        <br />
        <label>
          Reason:
          <input
            type="text"
            name="reason"
            value={formData.reason}
            onChange={handleChange}
            required
          />
        </label>
        <br />
        <button type="submit">Submit</button>
      </form>
      {responseMessage && <p>{responseMessage}</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
}

export default DonXinNghi;
