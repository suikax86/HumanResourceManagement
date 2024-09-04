import ShowSheet from "../../components/ShowTimeSheet/ShowTimeSheet.jsx";
import Header from "../../components/common/Header/Header.jsx";
import Footer from "../../components/common/Footer/Footer.jsx";
import "./ShowSheet.scss";

function Don() {
  return (
    <div className="my-profile-container">
      <Header />
      <div className="content">
        <ShowSheet />
      </div>
      <Footer />
    </div>
  );
}

export default Don;
