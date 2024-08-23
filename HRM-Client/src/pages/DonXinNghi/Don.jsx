import DonXinNghi from "../../components/DonXinNghi/DonXinNghi.jsx";
import Header from "../../components/common/Header/Header.jsx";
import Footer from "../../components/common/Footer/Footer.jsx";
import "./Don.scss";

function Don() {
  return (
    <div className="my-profile-container">
      <Header />
      <div className="content">
        <DonXinNghi />
      </div>
      <Footer />
    </div>
  );
}

export default Don;
