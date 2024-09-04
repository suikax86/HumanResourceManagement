import './ApprovalHistoryPage.scss';
import Header from "../../components/common/Header/Header.jsx";
import Footer from "../../components/common/Footer/Footer.jsx";
import ApprovalHistory from "../../components/ApprovalHistory/ApprovalHistory.jsx";
function ApprovalHistoryPage() {
    return (
        <div className="my-profile-container">
          <Header />
          <div className="content">
            <ApprovalHistory />
          </div>
          <Footer />
        </div>
      );
}

export default ApprovalHistoryPage;