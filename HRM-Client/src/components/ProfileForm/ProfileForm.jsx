import { useState, useEffect } from "react";
import axios from "axios";
import "./ProfileForm.scss";
import LoadingSpinner from "../common/LoadingSpinner/LoadingSpinner.jsx";

function ProfileForm() {
  const [employee, setEmployee] = useState(null);

  useEffect(() => {
    const fetchEmployee = async () => {
      try {
        const userInfo = JSON.parse(localStorage.getItem("userInfo"));
        const employeeId = userInfo.employeeId;
        const response = await axios.get(`http://localhost:8080/api/employees/${employeeId}`);
        setEmployee(response.data);
      } catch (error) {
        console.error("Error fetching employee data:");
      }
    };

    // Call fetchEmployee and handle the promise
    fetchEmployee();
  }, []);

  if (!employee) {
    return <LoadingSpinner />;
  }

  return (
    <div className="profile-form">
      <h1>Hồ sơ của tôi</h1>
      <div className="profile-columns">
        <div className="profile-column">
          <div className="profile-row">
            <div className="profile-label">Mã nhân viên:</div>
            <div className="profile-value">{employee.id}</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Tên nhân viên:</div>
            <div className="profile-value">{employee.name}</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Email:</div>
            <div className="profile-value">{employee.email}</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Chức vị:</div>
            <div className="profile-value">{employee.role}</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Căn cước:</div>
            <div className="profile-value">{employee.idNumber}</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Mã số thuế:</div>
            <div className="profile-value">{employee.taxNumber}</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Số điện thoại:</div>
            <div className="profile-value">{employee.phoneNumber}</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Địa chỉ:</div>
            <div className="profile-value">{employee.address}</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Tên ngân hàng:</div>
            <div className="profile-value">{employee.bankName}</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Số ngân hàng:</div>
            <div className="profile-value">{employee.bankNumber}</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Số điểm thưởng:</div>
            <div className="profile-value">{employee.rewardPoints}</div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ProfileForm;