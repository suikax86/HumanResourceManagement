import ProfileForm from "../../components/ProfileForm/ProfileForm.js";
import Header from "../../components/common/Header/Header.js";
import Footer from "../../components/common/Footer/Footer.js";
import "./MyProfile.scss";

function MyProfile() {
  return (
    <div className="my-profile-container">
      <Header />
      <div className="content">
        <ProfileForm />
      </div>
      <Footer />
    </div>
  );
}

export default MyProfile;
