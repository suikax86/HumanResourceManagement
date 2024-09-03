import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './HistoryApplication.scss';

function HistoryApplication() {
    const [selectedRow, setSelectedRow] = useState(null);
    const [forms, setForms] = useState([]);
    const [error, setError] = useState('');
    const [responseMessage, setResponseMessage] = useState('');
    const [formData, setFormData] = useState({
        id: '',
        employeeId: '',
        name: '',
        phone: '',
        dayRest: '',
        dateBackToWork: '',
        type: '',
        formCondition: '',
        reason: ''
    });

    const EmployeeInfo = localStorage.getItem('userInfo');
    const user = JSON.parse(EmployeeInfo);

    // Fetch forms from the API
    const fetchForms = () => {
        fetch('http://localhost:8080/api/forms')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setForms(data);
            })
            .catch(error => {
                setError(error);
            });
    };

    useEffect(() => {
        fetchForms();
    }, []);

    const handleRowClick = (index, form) => {
        setSelectedRow(index);
        setFormData({
            id: form.id,
            employeeId: form.employeeId,
            name: form.name,
            phone: form.phone,
            dayRest: form.dayRest,
            dateBackToWork: form.dateBackToWork,
            type: form.type,
            formCondition: form.formCondition,
            reason: form.reason
        });
    };

    const XoaDon = () => {
        axios.delete(`http://localhost:8080/api/forms/${formData.id}?employeeId=${user.employeeId}`)
            .then(response => {
                setResponseMessage('Form deleted successfully!');
                setError('');
                // Refresh the forms list
                fetchForms();
            })
            .catch(err => {
                setError('Error deleting form.');
                setResponseMessage('');
            });
    };

    const KhongDuyetDon = () => {
        axios.put(`http://localhost:8080/api/forms/${formData.id}`, {
            id: formData.id,
            employeeId: formData.employeeId,
            name: formData.name,
            phone: formData.phone,
            dayRest: formData.dayRest,
            dateBackToWork: formData.dateBackToWork,
            type: formData.type,
            formCondition: "Denied",
            reason: formData.reason
        })
            .then(response => {
                setResponseMessage('Form updated successfully!');
                setError('');
                // Refresh the forms list
                fetchForms();
            })
            .catch(err => {
                setError('Error updating form.');
                setResponseMessage('');
            });
    };

    return (
        <>
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr>
                        <th style={{ border: '1px solid black', padding: '10px' }}>Mã đơn</th>
                        <th style={{ border: '1px solid black', padding: '10px' }}>Mã nhân viên</th>
                        <th style={{ border: '1px solid black', padding: '10px' }}>Tên nhân viên</th>
                        <th style={{ border: '1px solid black', padding: '10px' }}>Ngày bắt đầu</th>
                        <th style={{ border: '1px solid black', padding: '10px' }}>Ngày kết thúc</th>
                        <th style={{ border: '1px solid black', padding: '10px' }}>Loại đơn</th>
                        <th style={{ border: '1px solid black', padding: '10px' }}>Lý Do</th>
                        <th style={{ border: '1px solid black', padding: '10px' }}>Mã người duyệt</th>
                        <th style={{ border: '1px solid black', padding: '10px' }}>Người Duyệt</th>
                        <th style={{ border: '1px solid black', padding: '10px' }}>Tình trạng đơn</th>
                        <th style={{ border: '1px solid black', padding: '10px' }}>Bình luận</th>
                    </tr>
                </thead>
                <tbody>
                    {forms
                        .filter(person => person.employeeId === user.employeeId)
                        .map((person, index) => (
                            <tr
                                key={person.id}
                                onClick={() => handleRowClick(index, person)}
                                style={{
                                    backgroundColor: selectedRow === index ? 'lightblue' : 'white',
                                    cursor: 'pointer',
                                }}
                            >
                                <td style={{ border: '1px solid black', padding: '10px' }}>{person.id}</td>
                                <td style={{ border: '1px solid black', padding: '10px' }}>{person.employeeId}</td>
                                <td style={{ border: '1px solid black', padding: '10px' }}>{person.name}</td>
                                <td style={{ border: '1px solid black', padding: '10px' }}>{person.startDate}</td>
                                <td style={{ border: '1px solid black', padding: '10px' }}>{person.endDate}</td>
                                <td style={{ border: '1px solid black', padding: '10px' }}>{person.formType}</td>
                                <td style={{ border: '1px solid black', padding: '10px' }}>{person.reason}</td>
                                <td style={{ border: '1px solid black', padding: '10px' }}>{person.approverId}</td>
                                <td style={{ border: '1px solid black', padding: '10px' }}>{person.approverName}</td>
                                <td style={{ border: '1px solid black', padding: '10px', backgroundColor:
                                    person.formStatus === 'PENDING' ? 'purple' :
                                    person.formStatus === 'APPROVED' ? 'green' :
                                    person.formStatus === 'REJECTED' ? 'red' : 'white' }}>{person.formStatus}</td>
                                <td style={{ border: '1px solid black', padding: '10px' }}>{person.comment}</td>
                            </tr>
                        ))}
                </tbody>
            </table>

            <div className="button-container">
                <button onClick={XoaDon} className='KoDuyet'>Xóa đơn</button>
              
            </div>
        </>
    );
}

export default HistoryApplication;
