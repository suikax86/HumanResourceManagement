import { useState } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./LoginForm.scss";

function LoginForm() {
  const [email, setEmail] = useState("");
  const [pass, setPass] = useState("");

  const handleChangeEmail = (e) => {
    setEmail(e.target.value);
  };

  const handleChangePass = (e) => {
    setPass(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/api/auth/login", {
        email: email,
        password: pass,
      });
      
      if (response.status === 200) {
        localStorage.setItem("userInfo", JSON.stringify(response.data));
        toast.success("Login successful!");
        // Redirect to home page
        window.location.href = "/Homepage";
      }
    } catch (error) {
      if (error.response && error.response.status === 404) {
        toast.error("Invalid email or password");
      } else {
        toast.error("An error occurred. Please try again.");
      }
    }
  };

  return (
    <div className="Loginform_container">
      <ToastContainer />
      <div className="Loginform_title">Sign in to your account</div>
      <form onSubmit={handleSubmit}>
        <div className="Loginform_email">
          <div className="Loginform_email_title">Email</div>
          <input
            type="text"
            placeholder="youremail@gmail.com"
            id="email"
            value={email}
            onChange={handleChangeEmail}
            className="Loginform_email_input"
          />
        </div>
        <div className="Loginform_pass">
          <div className="Loginform_pass_title">Password</div>
          <input
            type="password"
            placeholder="Your password"
            id="password"
            value={pass}
            onChange={handleChangePass}
            className="Loginform_pass_input"
          />
        </div>
        <div className="Loginform_remember_forget">
          <div className="Loginform_remember">
            <input type="checkbox" />
            <div className="Loginform_remember_text">Remember me</div>
          </div>
          <a href="#" className="Loginform_forget">
            Forgot password ?
          </a>
        </div>
        <button type="submit" className="Loginform_button">Sign in</button>
      </form>
    </div>
  );
}

export default LoginForm;