import React, { useState } from 'react';
import axios from 'axios';
import './DonXinNghi.scss';
function DonXinNghi() {


  const EmployeeInfo = localStorage.getItem('userInfo');
  const user = JSON.parse(EmployeeInfo);

  const [formData, setFormData] = useState({
    employeeId: user.employeeId,
    // name: '',
    // phone: '',
    startDate: '',
    endDate: '',
    formType: '',
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
  const today = new Date().toISOString().split('T')[0];

  // Function to validate the date inputs
  const isDayRestValid = formData.startDate === '' || formData.endDate === '' || formData.startDate <= formData.endDate;


  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("formData", formData);
    
    axios.post('http://localhost:8080/api/forms/', formData)
      .then(response => {
        setResponseMessage('Form submitted successfully!');
        setError('');
        // Optionally reset the form
        setFormData({
          employeeId: '',
          // name: '',
          // phone: '',
          startDate: '',
          endDate: '',
          formType: '',
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


        {/* <label>
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
        </label> */}


        <br />
        <label>
          Day Rest:
          <input
            type="date"
            name="startDate"
            value={formData.startDate}
            onChange={handleChange}
            required
          min={today} // Sets the min allowable date to today
          max={formData.startDate} // Sets the max allowable date to Date Back to Work
          />
        </label>
        <br />
        <label>
          Date Back to Work:
          <input
            type="date"
            name="endDate"
            value={formData.endDate}
            onChange={handleChange}
            required
          />
        </label>

        {!isDayRestValid && (
        <p style={{ color: 'red' }}>Day Rest cannot be later than Date Back to Work.</p>
      )}
        <br />
        <label>
        <select
    name="formType"
    value={formData.formType}
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
        <button type="submit"  disabled={!isDayRestValid}>Submit</button>
      </form>
      {responseMessage && <p>{responseMessage}</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
}

export default DonXinNghi;
