import WorkFromHome from "../../components/WorkFromHome/WorkFromHome.jsx";
import Header from "../../components/common/Header/Header.jsx";
import Footer from "../../components/common/Footer/Footer.jsx";
import "./WorkHome.scss";

function WorkHome() {
  return (
    <div className="my-profile-container">
      <Header />
      <div className="content">
        <WorkFromHome />
      </div>
      <Footer />
    </div>
  );
}

export default WorkHome;
