import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './DuyetDonNV.scss';

function DuyetDonNV() {
    const [selectedRowForPending, setSelectedRowForPending] = useState(null);
    const [forms, setForms] = useState([]);
    const [error, setError] = useState('');
    const [responseMessage, setResponseMessage] = useState('');


    const EmployeeInfo = localStorage.getItem('userInfo');
  const user = JSON.parse(EmployeeInfo);
    const [formData, setFormData] = useState({
        id: user.employeeId,
        employeeId: '',
        name: '',
        phone: '',
        startDate: '',
        endDate: '',
        formType: '',
        formCondition: '',
        reason: ''
    });

    const [approved, setApproved] = useState({ 
      approverId: "",
      formStatus: "APPROVED",
      comment : ""

    });
    const [isModalOpen,   setIsModalOpen] = useState(false);
    const [comment, setComment] = useState('');

    useEffect(() => {
        fetch('http://localhost:8080/api/forms/')
            .then(response => response.json())
            .then(data => setForms(data))
            .catch(error => setError(error));
    }, []);

    const handleRowClickForPending = (index, form) => {
        setSelectedRowForPending(index);
        setFormData(form);
    };

    const handleApprove = async () => {
      // Create the approval object
      const approvalData = {
          approverId: user.employeeId,
          formStatus: "APPROVED",
          comment: comment
      };
      console.log("approvalData",approvalData);
      
      try {
          // Make the API call to update the form status
          await axios.put(`http://localhost:8080/api/forms/${formData.id}`, approvalData);
  
          // Update the state with the approved form
          setForms(forms.map(form => 
              form.id === formData.id 
              ? { ...form, formStatus: 'APPROVED', comment } 
              : form
          ));
          
          // Clear the modal and form data
          setIsModalOpen(false);
          setComment('');
          setResponseMessage('Form approved successfully!');
      } catch (err) {
          setError('Error updating form.');
          setResponseMessage('');
      }
  };

    const handleReject = () => {
        axios.put(`http://localhost:8080/api/forms/${formData.id}`, {
            ...formData,
            formCondition: 'Denied'
        })
        .then(response => {
            setResponseMessage('Form rejected successfully!');
            setForms(forms.map(form => (form.id === formData.id ? { ...form, formStatus: 'REJECTED' } : form)));
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
                        <th style={{ border: '1px solid black', padding: '10px' }}>Tình trạng đơn</th>
                    </tr>
                </thead>
                <tbody>
                    {forms.filter(person => person.formStatus === "PENDING") 
                    .map((person, index) => ( 
                        <tr
                            key={person.id}
                            onClick={() => handleRowClickForPending(index, person)}
                            style={{
                                backgroundColor: selectedRowForPending === index ? 'lightblue' : 'white',
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
                            <td style={{ border: '1px solid black', padding: '10px', backgroundColor:
                                person.formStatus === 'PENDING' ? 'purple' :
                                person.formStatus === 'APPROVED' ? 'green' :
                                person.formStatus === 'REJECTED' ? 'red' : 'white'
                            }}>{person.formStatus}</td>
                        </tr>
                    ))}
                </tbody>
            </table>

            <div className="button-container">
                <button onClick={() => setIsModalOpen(true)} className='Duyet'>Duyệt</button>
                <button onClick={handleReject} className='KoDuyet'>Từ Chối</button>
            </div>

            {isModalOpen && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h2>Add Comment</h2>
                            <button className="close-button" onClick={() => setIsModalOpen(false)}>×</button>
                        </div>
                        <textarea 
                            value={comment}
                            onChange={(e) => setComment(e.target.value)}
                            rows="4"
                            placeholder="Enter your comment here..."
                        />
                        <div className="modal-buttons">
                            <button onClick={() => {
                                handleApprove();
                                setFormData(formData); // Set the formData before making the API call
                            }} className="approve-button">Approve</button>
                            <button onClick={() => setIsModalOpen(false)} className="cancel-button">Cancel</button>
                        </div>
                    </div>
                </div>
            )}

            {error && <div className="error-message">{error}</div>}
            {responseMessage && <div className="response-message">{responseMessage}</div>}
        </>
    );
}

export default DuyetDonNV;
