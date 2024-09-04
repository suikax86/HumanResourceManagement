import React, { useState } from 'react';
import axios from 'axios';
import './DonXinNghi.scss';
import { NavLink } from "react-router-dom";

function DonXinNghi() {
  const EmployeeInfo = localStorage.getItem('userInfo');
  const user = JSON.parse(EmployeeInfo);

  const [formData, setFormData] = useState({
    employeeId: user.employeeId,
    startDate: '',
    endDate: '',
    formType: '',
    reason: ''
  });

  
  const [selectedOption, setSelectedOption] = useState('');

  const handleTypeChange = (event) => {
    setSelectedOption(event.target.value);
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
    
    axios.post('http://localhost:8080/api/forms', formData)
      .then(response => {
        setResponseMessage('Form submitted successfully!');
        setError('');
        setFormData({
          employeeId: '',
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

  const cancelSubmit = () => {

  }

  return (
    <div className='request-container'>
      <di className='request-title'>Submit a Form</di>
      <form onSubmit={handleSubmit} className='request-form'>
        <div className='form-detail'>
          <label>
            Employee ID :
          </label>
          <input
              type="number"
              name="employeeId"
              value={user.employeeId}
              disabled
              className='employeeid-input'
            />
        </div>
        <div className='form-detail'>
          <label>
            Start Date :
          </label>
          <input
            type="date"
            name="startDate"
            value={formData.startDate}
            onChange={handleChange}
            required
            className='form-input'
          min={today} // Sets the min allowable date to today
          max={formData.startDate} // Sets the max allowable date to Date Back to Work
          />
        </div>

        <div className='form-detail'>
          <label>
            End Date :
          </label>
          <input
            type="date"
            name="endDate"
            value={formData.endDate}
            onChange={handleChange}
            className='form-input'
            required
          />
        </div>

        {!isDayRestValid && (
        <p style={{ color: 'red' }}>Day Rest cannot be later than Date Back to Work.</p>
      )}
        <select
          name="formType"
          value={formData.formType}
          onChange={handleChange}
          className='form-select'
          required
        >
          <option value="" disabled>Type of rest</option>
          <option value="WFH">WFH</option>
          <option value="LEAVE">Nghỉ phép</option>
        </select>
        <div className='form-detail'>
          <label>
            Reason:
          </label>
          <textarea
            type="text"
            name="reason"
            value={formData.reason}
            onChange={handleChange}
             className='form-input'
            required
          />
        </div>
        <div className='form-button'>
          <button type="submit"  disabled={!isDayRestValid} className='button-submit'>Submit</button>
          <NavLink to={'/homepage'} className='button-cancel'>Cancel</NavLink>
        </div>
      </form>
      {responseMessage && <p>{responseMessage}</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
}

export default DonXinNghi;
