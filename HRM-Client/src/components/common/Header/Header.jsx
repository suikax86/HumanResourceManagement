import { NavLink } from "react-router-dom";
import "./Header.scss";
import { useNavigate } from "react-router-dom";
import { Dropdown, Space } from 'antd';



const profileEm  = [
    {
        key: '1',
        label: (
            <NavLink to="/MyProfile" className={'header_dropdown'}>
                Hồ sơ của tôi
            </NavLink>
        ),
    },
    {
        key: '2',
        label: (
            <NavLink to="/EditProfile" className={'header_dropdown'}>
                Chỉnh sửa hồ sơ
            </NavLink>
        ),
    }
];

const profileMa  = [
    {
        key: '1',
        label: (
            <NavLink to="/MyProfile" className={'header_dropdown'}>
                Hồ sơ của tôi
            </NavLink>
        ),
    },
    {
        key: '2',
        label: (
            <NavLink to="/EditProfile" className={'header_dropdown'}>
                Chỉnh sửa hồ sơ
            </NavLink>
        ),
    },
    {
        key: '3',
        label: (
            <NavLink to="/CreateProfile" className={'header_dropdown'}>
                Tạo hồ sơ nhân viên
            </NavLink>
        ),
    },
];
const requestEm  = [
    {
        key: '1',
        label: (
            <NavLink to="/DonXinNghi" className={'header_dropdown'}>
                Tạo yêu cầu
            </NavLink>
        ),
    },
    {
        key: '2',
        label: (
            <NavLink to="/History" className={'header_dropdown'}>
                Yêu cầu của tôi
            </NavLink>
        ),
    },
    {
        key: '3',
        label: (
            <NavLink to="/CheckIn-CheckOut" className={'header_dropdown'}>
                Check in - Check out
            </NavLink>
        ),
    },
    {
        key: '4',
        label: (
            <NavLink to="/TimeSheet" className={'header_dropdown'}>
                TimeSheet
            </NavLink>
        ),
    },
    {
        key: '5',
        label: (
            <NavLink to="/DuyetDon" className={'header_dropdown'}>
                Duyệt đơn nhân viên
            </NavLink>
        ),
    },
    {
        key: '6',
        label: (
            <NavLink to="/ShowTimeSheet" className={'header_dropdown'}>
                Xem giờ làm
            </NavLink>
        ),
    },
];
const requestMa  = [
    {
        key: '1',
        label: (
            <NavLink to="/DonXinNghi" className={'header_dropdown'}>
                Tạo yêu cầu
            </NavLink>
        ),
    },
    {
        key: '2',
        label: (
            <NavLink to="/History" className={'header_dropdown'}>
                Yêu cầu của tôi
            </NavLink>
        ),
    },
    {
        key: '3',
        label: (
            <NavLink to="/CheckIn-CheckOut" className={'header_dropdown'}>z
                Check in - Check out
            </NavLink>
        ),
    },
    {
        key: '4',
        label: (
            <NavLink to="/DuyetDon" className={'header_dropdown'}>
                Duyệt yêu cầu
            </NavLink>
        ),
    },
    {
        key: '5',
        label: (
            <NavLink to="/ApprovalHistory" className={'header_dropdown'}>
                Lịch sử duyệt yêu cầu
            </NavLink>
        ),
    },
    {
        key: '6',
        label: (
            <NavLink to="/TimeSheet" className={'header_dropdown'}>
                TimeSheet
            </NavLink>
        ),
    },
    {
        key: '7',
        label: (
            <NavLink to="/ShowTimeSheet" className={'header_dropdown'}>
                Xem giờ làm
            </NavLink>
        ),
    },


];
const activityEm  = [
    {
        key: '1',
        label: (
            <NavLink to="/" className={'header_dropdown'}>
                Hoạt động của tôi
            </NavLink>
        ),
    },
    {
        key: '2',
        label: (
            <NavLink to="/strava-auth" className={'header_dropdown'}>
                Hoạt động strava
            </NavLink>
        ),
    },
    {
        key: '3',
        label: (
            <NavLink to="/" className={'header_dropdown'}>
                Đăng kí hoạt động
            </NavLink>
        ),
    },
];
const activityMa  = [
    {
        key: '1',
        label: (
            <NavLink to="/" className={'header_dropdown'}>
                Hoạt động của tôi
            </NavLink>
        ),
    },
    {
        key: '2',
        label: (
            <NavLink to="/strava-auth" className={'header_dropdown'}>
                Hoạt động strava
            </NavLink>
        ),
    },
    {
        key: '3',
        label: (
            <NavLink to="/" className={'header_dropdown'}>
                Đăng kí hoạt động
            </NavLink>
        ),
    },
    {
        key: '4',
        label: (
            <NavLink to="/" className={'header_dropdown'}>
                Tạo hoạt động
            </NavLink>
        ),
    },
];
const pointEm  = [
    {
        key: '1',
        label: (
            <NavLink to="/" className={'header_dropdown'}>
                Điểm thưởng của tôi
            </NavLink>
        ),
    },
    {
        key: '2',
        label: (
            <NavLink to="/voucher" className={'header_dropdown'}>
                Voucher
            </NavLink>
        ),
    },
    {
        key: '3',
        label: (
            <NavLink to="/myvoucher" className={'header_dropdown'}>
                Voucher của tôi
            </NavLink>
        ),
    }
];
const pointMa  = [
    {
        key: '1',
        label: (
            <NavLink to="/" className={'header_dropdown'}>
                Điểm thưởng của tôi
            </NavLink>
        ),
    },

    {
        key: '2',
        label: (
            <NavLink to="/transfer-point" className={'header_dropdown'}>
                Chuyển điểm thưởng
            </NavLink>
        ),
    },
    {
        key: '3',
        label: (
            <NavLink to="/voucher" className={'header_dropdown'}>
                Voucher
            </NavLink>
        ),
    },
    {
        key: '4',
        label: (
            <NavLink to="/myvoucher" className={'header_dropdown'}>
                Voucher của tôi
            </NavLink>
        ),
    },
    {
        key: '5',
        label: (
            <NavLink to="/" className={'header_dropdown'}>
                Tạo voucher
            </NavLink>
        ),
    },
];
function Header() {
    let user = localStorage.getItem('userInfo')
    user = JSON.parse(user);
    const nav = useNavigate()
    const logOut = () => {
        localStorage.removeItem('userInfo')
        localStorage.removeItem('userId')
        nav('/')
    }
    return (
        <div className="header_container">
            <NavLink className="header_container_company_name" to="/HomePage">Công ty xyz</NavLink>
            <div className="header_container_nav">
                {
                    user.role === 'EMPLOYEE' ?  <Dropdown
                        placement="bottom"
                        menu={{
                            items : profileEm
                        }}
                        className="header_container_nav_detail"
                    >
                        <a onClick={(e) => e.preventDefault()}>
                            <Space>
                                Hồ sơ
                            </Space>
                        </a>
                    </Dropdown> :  <Dropdown
                        placement="bottom"
                        menu={{
                            items : profileMa
                        }}
                        className="header_container_nav_detail"
                    >
                        <a onClick={(e) => e.preventDefault()}>
                            <Space>
                                Hồ sơ
                            </Space>
                        </a>
                    </Dropdown>
                }
                {
                    user.role === 'EMPLOYEE' ?  <Dropdown
                        placement="bottom"
                        menu={{
                            items : requestEm
                        }}
                        className="header_container_nav_detail"
                    >
                        <a onClick={(e) => e.preventDefault()}>
                            <Space>
                                Yêu cầu
                            </Space>
                        </a>
                    </Dropdown> :  <Dropdown
                        placement="bottom"
                        menu={{
                            items : requestMa
                        }}
                        className="header_container_nav_detail"
                    >
                        <a onClick={(e) => e.preventDefault()}>
                            <Space>
                                Yêu cầu
                            </Space>
                        </a>
                    </Dropdown>
                }
                {
                    user.role === 'EMPLOYEE' ?  <Dropdown
                        placement="bottom"
                        menu={{
                            items : activityEm
                        }}
                        className="header_container_nav_detail"
                    >
                        <a onClick={(e) => e.preventDefault()}>
                            <Space>
                                Hoạt động
                            </Space>
                        </a>
                    </Dropdown> :  <Dropdown
                        placement="bottom"
                        menu={{
                            items : activityMa
                        }}
                        className="header_container_nav_detail"
                    >
                        <a onClick={(e) => e.preventDefault()}>
                            <Space>
                                Hoạt động
                            </Space>
                        </a>
                    </Dropdown>
                }
                {
                    user.role === 'EMPLOYEE' ?  <Dropdown
                        placement="bottom"
                        menu={{
                            items : pointEm
                        }}
                        className="header_container_nav_detail"
                    >
                        <a onClick={(e) => e.preventDefault()}>
                            <Space>
                                Điểm thưởng
                            </Space>
                        </a>
                    </Dropdown> :  <Dropdown
                        placement="bottom"
                        menu={{
                            items : pointMa
                        }}
                        className="header_container_nav_detail"
                    >
                        <a onClick={(e) => e.preventDefault()}>
                            <Space>
                                Điểm thưởng
                            </Space>
                        </a>
                    </Dropdown>
                }
            </div>
            <div onClick={() => logOut()}>
                <button className="header_container_button">Log out</button>
            </div>
        </div>
    );
}

export default Header;