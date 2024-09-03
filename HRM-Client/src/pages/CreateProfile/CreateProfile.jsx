import Header from "../../components/common/Header/Header.jsx";
import Footer from "../../components/common/Footer/Footer.jsx";
import CreateProfileForm from "../../components/CreateProfileForm/CreateProfileForm.jsx";

function CreateProfile() {
  return (
    <div className="my-profile-container">
      <Header />
      <div className="content">
        <CreateProfileForm />
      </div>
      <Footer />
    </div>
  );
}

export default CreateProfile;
