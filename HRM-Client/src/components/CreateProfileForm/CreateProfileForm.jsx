import { useState } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "../ProfileForm/ProfileForm.scss";

const CreateProfileForm = () => {
  const [employee, setEmployee] = useState({
    email: "",
    password: "",
    name: "",
    idNumber: "",
    taxNumber: "",
    address: "",
    phoneNumber: "",
    bankName: "",
    bankNumber: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEmployee((prevEmployee) => ({
      ...prevEmployee,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        `http://localhost:8080/api/auth/register`,
        employee
      );
      if (response.status === 201) {
        toast.success("Create employee profile successful!");
      }
    } catch (error) {
      console.error(
        "Error creating employee profile data:",
        error.response.data.message
      );
      toast.error("Error creating employee profile data:");
    }
  };

  return (
    <>
      <form className="profile-form" onSubmit={handleSubmit}>
        <h1>Tạo hồ sơ nhân viên </h1>
        <div>
          <label>Email nhân viên:</label>
          <input
            type="text"
            name="email"
            placeholder="example@gmail.com"
            value={employee.email}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Password:</label>
          <input
            type="password"
            name="password"
            placeholder="Demo123"
            value={employee.password}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Tên nhân viên:</label>
          <input
            type="text"
            name="name"
            placeholder="Nguyễn Văn A"
            value={employee.name}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Chứng minh nhân dân:</label>
          <input
            type="text"
            name="idNumber"
            placeholder="437179775954"
            value={employee.idNumber}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Mã số thuế cá nhân:</label>
          <input
            type="text"
            name="taxNumber"
            placeholder="3042746957285"
            value={employee.taxNumber}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Address:</label>
          <input
            type="text"
            name="address"
            placeholder="TP HCM"
            value={employee.address}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Phone Number:</label>
          <input
            type="text"
            name="phoneNumber"
            placeholder="01234123123"
            value={employee.phoneNumber}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Bank Name:</label>
          <input
            type="text"
            name="bankName"
            placeholder="TP Bank"
            value={employee.bankName}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Bank Number:</label>
          <input
            type="text"
            name="bankNumber"
            placeholder="0123456789"
            value={employee.bankNumber}
            onChange={handleChange}
          />
        </div>

        <button type="submit">Create Profile</button>
      </form>
      <ToastContainer position="bottom-right" />
    </>
  );
};

export default CreateProfileForm;
