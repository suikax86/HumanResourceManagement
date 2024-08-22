import ProfileForm from "../../components/ProfileForm/ProfileForm";
import Header from "../../components/common/Header/Header.jsx";
import Footer from "../../components/common/Footer/Footer.jsx";
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
