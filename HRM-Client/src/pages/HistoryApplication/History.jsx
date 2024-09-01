import HistoryApplication from "../../components/HistoryApplication/HistoryApplication.jsx";
import Header from "../../components/common/Header/Header.jsx";
import Footer from "../../components/common/Footer/Footer.jsx";
import "./History.scss";

function History() {
  return (
    <div className="my-profile-container">
      <Header />
      <div className="content">
        <HistoryApplication />
      </div>
      <Footer />
    </div>
  );
}

export default History;
