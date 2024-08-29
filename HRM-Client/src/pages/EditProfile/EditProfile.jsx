import Header from "../../components/common/Header/Header.jsx";
import Footer from "../../components/common/Footer/Footer.jsx";
import EditProfileForm from "../../components/EditProfileForm/EditProfileForm.jsx";

function EditProfile() {
    return (
        <div className="my-profile-container">
          <Header />
          <div className="content">
            <EditProfileForm />
          </div>
          <Footer />
        </div>
      );
}
export default EditProfile;