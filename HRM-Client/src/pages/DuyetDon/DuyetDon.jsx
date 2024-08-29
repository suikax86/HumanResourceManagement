import DuyetDonNV from "../../components/DuyetDonNV/DuyetDonNV.jsx";
import Header from "../../components/common/Header/Header.jsx";
import Footer from "../../components/common/Footer/Footer.jsx";
import "./DuyetDon.scss";

function DuyetDon() {
  return (
    <div className="my-profile-container">
      <Header />
      <div className="content">
        <DuyetDonNV />
      </div>
      <Footer />
    </div>
  );
}

export default DuyetDon;
