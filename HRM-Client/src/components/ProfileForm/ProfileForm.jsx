import "./ProfileForm.scss";

function ProfileForm() {
  return (
    <div className="profile-form">
      <h1>Employee Profile</h1>
      <div className="profile-columns">
        <div className="profile-column">
          <div className="profile-row">
            <div className="profile-label">Mã nhân viên:</div>
            <div className="profile-value">NV001</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Tên nhân viên:</div>
            <div className="profile-value">Nguyễn Văn A</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Email:</div>
            <div className="profile-value">example@gmail.com</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Chức vị:</div>
            <div className="profile-value">Quản lý</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Căn cước:</div>
            <div className="profile-value">772815679695</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Mã số thuế:</div>
            <div className="profile-value">9476401545346</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Số điện thoại:</div>
            <div className="profile-value">0123456789</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Địa chỉ:</div>
            <div className="profile-value">Sai Gon</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Tên ngân hàng:</div>
            <div className="profile-value">Tiên Phong Bank</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Số ngân hàng:</div>
            <div className="profile-value">034034079</div>
          </div>
          <div className="profile-row">
            <div className="profile-label">Số điểm thưởng:</div>
            <div className="profile-value">200</div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ProfileForm;