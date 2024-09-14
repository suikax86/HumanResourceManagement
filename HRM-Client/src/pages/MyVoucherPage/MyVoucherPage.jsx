import MyVoucher  from "../../components/MyVoucher/MyVoucher.jsx";
import Header from "../../components/common/Header/Header.jsx";
import Footer from "../../components/common/Footer/Footer.jsx";

function MyVoucherPage() {
    return (
        <div className="my-profile-container">
            <Header />
            <div className="content">
                <MyVoucher/>
            </div>
            <Footer />
        </div>
    );
}

export default MyVoucherPage;
