import { NavLink } from "react-router-dom";
import "./Header.scss";

function Header() {
  return (
    <div className="header_container">
      <div className="header_container_company_name">Công ty xyz</div>
      <div className="header_container_nav">
        <NavLink to="/">Trang chủ</NavLink>
        <NavLink to="/MyProfile">
          Hồ sơ của tôi
        </NavLink>
        <a href="http://www.localhost:80/myactivities"> Hoạt động của tôi</a>
        <NavLink to="/DonXinNghi">
          Xin nghỉ phép
        </NavLink>
        <NavLink to="/WorkFromHome">
          Work From Home
        </NavLink>
        
      </div>
      <NavLink to="/login">
        <button className="header_container_button">Log in</button>
      </NavLink>
    </div>
  );
}

export default Header;
