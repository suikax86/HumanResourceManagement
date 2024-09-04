import React, { useEffect, useState } from 'react';
import axios from 'axios';
import "./ShowTimeSheet.scss";

const ShowTimeSheet = () => {
  const [timeSheets, setTimeSheets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const EmployeeInfo = localStorage.getItem('userInfo');
  const user = JSON.parse(EmployeeInfo);

  // Function to fetch timesheets
  const fetchTimeSheets = () => {
    setLoading(true);
    axios.get(`http://localhost:8081/api/timesheet/get/${user.employeeId}`)
      .then(response => {
        setTimeSheets(response.data);
        setLoading(false);
      })
      .catch(error => {
        console.error('There was an error fetching the data!', error);
        setError('Failed to fetch timesheets.');
        setLoading(false);
      });
  };

  // Fetch timesheets on component mount
  useEffect(() => {
    fetchTimeSheets();
  }, [user.employeeId]);

  const handleDelete = (id) => {
    axios.delete(`http://localhost:8081/api/timesheet/delete/${id}`)
      .then(response => {
        alert(response.data); // Assuming the response is a message
        fetchTimeSheets(); // Refetch the timesheets list after deletion
      })
      .catch(error => {
        console.error('There was an error deleting the timesheet!', error);
        alert('Failed to delete timesheet.');
      });
  };

  return (
    <div>
      <h1>TimeSheet Records</h1>
      {loading ? (
        <p>Loading...</p>
      ) : error ? (
        <p>{error}</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Employee ID</th>
              <th>Name</th>
              <th>Date</th>
              <th>Time Begin</th>
              <th>Time End</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {timeSheets.map(sheet => (
              <tr key={sheet.id}>
                <td>{sheet.employeeId}</td>
                <td>{sheet.name}</td>
                <td>{sheet.date}</td>
                <td>{sheet.timeBegin}</td>
                <td>{sheet.timeEnd}</td>
                <td>
                  <button onClick={() => handleDelete(sheet.id)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default ShowTimeSheet;
