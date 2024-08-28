import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './EditProfileForm.scss'; // Import the same SCSS file

const EditProfileForm = () => {
  const [employee, setEmployee] = useState({
    phoneNumber: '',
    address: '',
    bankName: '',
    bankNumber: '',
    rewardPoints: '',
    // Add other fields here if needed
  });

  useEffect(() => {
    const fetchEmployeeData = async () => {
      const employeeId = localStorage.getItem('employeeId');
      if (employeeId) {
        try {
          const response = await axios.get(`/api/employee/${employeeId}`);
          setEmployee(response.data);
        } catch (error) {
          console.error('Error fetching employee data:', error);
        }
      }
    };

    fetchEmployeeData();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEmployee((prevEmployee) => ({
      ...prevEmployee,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const employeeId = localStorage.getItem('employeeId');
    try {
      await axios.put(`/api/employee/${employeeId}`, employee);
      alert('Profile updated successfully');
    } catch (error) {
      console.error('Error updating profile:', error);
    }
  };

  return (
    <form className="profile-form" onSubmit={handleSubmit}>
      <div>
        <label>Phone Number:</label>
        <input
          type="text"
          name="phoneNumber"
          placeholder='01234123123'
          value={employee.phoneNumber}
          onChange={handleChange}
        />
      </div>
      <div>
        <label>Address:</label>
        <input
          type="text"
          name="address"
          value={employee.address}
          onChange={handleChange}
        />
      </div>
      <div>
        <label>Bank Name:</label>
        <input
          type="text"
          name="bankName"
          value={employee.bankName}
          onChange={handleChange}
        />
      </div>
      <div>
        <label>Bank Number:</label>
        <input
          type="text"
          name="bankNumber"
          value={employee.bankNumber}
          onChange={handleChange}
        />
      </div>
      <div>
        <label>Reward Points:</label>
        <input
          type="text"
          name="rewardPoints"
          value={employee.rewardPoints}
          onChange={handleChange}
        />
      </div>
      {/* Add other fields here if needed */}
      <button type="submit">Update Profile</button>
    </form>
  );
};

export default EditProfileForm;