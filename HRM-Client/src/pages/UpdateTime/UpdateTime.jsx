import UpdateTimeSheet from "../../components/UpdateTimeSheet/UpdateTimeSheet.jsx";
import Header from "../../components/common/Header/Header.jsx";
import Footer from "../../components/common/Footer/Footer.jsx";
import "./UpdateTime.scss";

function Don() {
  return (
    <div className="my-profile-container">
      <Header />
      <div className="content">
        <UpdateTimeSheet />
      </div>
      <Footer />
    </div>
  );
}

export default Don;
