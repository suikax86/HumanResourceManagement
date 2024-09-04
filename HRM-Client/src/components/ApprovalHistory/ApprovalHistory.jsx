import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ApprovalHistory.scss';

function ApprovalHistory() {
  const [approvedForms, setApprovedForms] = useState([]);
  const [rejectedForms, setRejectedForms] = useState([]);
  const [currentPageApproved, setCurrentPageApproved] = useState(1);
  const [currentPageRejected, setCurrentPageRejected] = useState(1);
  const formsPerPage = 5;

  useEffect(() => {
    const fetchApprovedForms = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/forms?formStatus=APPROVED');
        setApprovedForms(response.data);
      } catch (error) {
        console.error('Error fetching approved forms:', error);
      }
    };

    const fetchRejectedForms = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/forms?formStatus=REJECTED');
        setRejectedForms(response.data);
      } catch (error) {
        console.error('Error fetching rejected forms:', error);
      }
    };

    fetchApprovedForms();
    fetchRejectedForms();
  }, []);

  const indexOfLastApproved = currentPageApproved * formsPerPage;
  const indexOfFirstApproved = indexOfLastApproved - formsPerPage;
  const currentApprovedForms = approvedForms.slice(indexOfFirstApproved, indexOfLastApproved);

  const indexOfLastRejected = currentPageRejected * formsPerPage;
  const indexOfFirstRejected = indexOfLastRejected - formsPerPage;
  const currentRejectedForms = rejectedForms.slice(indexOfFirstRejected, indexOfLastRejected);

  const paginateApproved = (pageNumber) => setCurrentPageApproved(pageNumber);
  const paginateRejected = (pageNumber) => setCurrentPageRejected(pageNumber);

  return (
    <div className="approval-history-container">
      <h1>Lịch sử duyệt hồ sơ</h1>
      <div className="forms-section">
        <h2>Danh sách đơn được duyệt</h2>
        <table className="forms-table">
          <thead>
            <tr>
              <th>Mã đơn</th>
              <th>Mã nhân viên</th>
              <th>Mã người duyệt</th>
              <th>Tên nhân viên</th>
              <th>Tên người duyệt</th>
              <th>Số điện thoại</th>
              <th>Ngày bắt đầu</th>
              <th>Ngày kết thúc</th>
              <th>Loại đơn</th>
              <th>Lý do</th>
              <th>Bình luận</th>
            </tr>
          </thead>
          <tbody>
            {currentApprovedForms.map((form) => (
              <tr key={form.id}>
                <td>{form.id}</td>
                <td>{form.employeeId}</td>
                <td>{form.approverId}</td>
                <td>{form.name}</td>
                <td>{form.approverName}</td>
                <td>{form.phone}</td>
                <td>{form.startDate}</td>
                <td>{form.endDate}</td>
                <td>{form.formType}</td>
                <td>{form.reason}</td>
                <td>{form.comment}</td>
              </tr>
            ))}
          </tbody>
        </table>
        <Pagination
          formsPerPage={formsPerPage}
          totalForms={approvedForms.length}
          paginate={paginateApproved}
          currentPage={currentPageApproved}
        />
      </div>
      <div className="forms-section">
        <h2>Danh sách đơn bị từ chối</h2>
        <table className="forms-table">
          <thead>
          <tr>
              <th>Mã đơn</th>
              <th>Mã nhân viên</th>
              <th>Mã người duyệt</th>
              <th>Tên nhân viên</th>
              <th>Tên người duyệt</th>
              <th>Số điện thoại</th>
              <th>Ngày bắt đầu</th>
              <th>Ngày kết thúc</th>
              <th>Loại đơn</th>
              <th>Lý do</th>
              <th>Bình luận</th>
            </tr>
          </thead>
          <tbody>
            {currentRejectedForms.map((form) => (
              <tr key={form.id}>
                <td>{form.id}</td>
                <td>{form.employeeId}</td>
                <td>{form.approverId}</td>
                <td>{form.name}</td>
                <td>{form.approverName}</td>
                <td>{form.phone}</td>
                <td>{form.startDate}</td>
                <td>{form.endDate}</td>
                <td>{form.formType}</td>
                <td>{form.reason}</td>
                <td>{form.comment}</td>
              </tr>
            ))}
          </tbody>
        </table>
        <Pagination
          formsPerPage={formsPerPage}
          totalForms={rejectedForms.length}
          paginate={paginateRejected}
          currentPage={currentPageRejected}
        />
      </div>
    </div>
  );
}

function Pagination({ formsPerPage, totalForms, paginate, currentPage }) {
  const pageNumbers = [];

  for (let i = 1; i <= Math.ceil(totalForms / formsPerPage); i++) {
    pageNumbers.push(i);
  }

  return (
    <nav>
      <ul className="pagination">
        {pageNumbers.map((number) => (
          <li key={number} className={`page-item ${currentPage === number ? 'active' : ''}`}>
            <a onClick={() => paginate(number)} href="!#" className="page-link">
              {number}
            </a>
          </li>
        ))}
      </ul>
    </nav>
  );
}

export default ApprovalHistory;