import React, { useState, useEffect } from 'react';
import './UpdateTimeSheet.scss';
function UpdateTimeSheet() {
    const EmployeeInfo = localStorage.getItem('userInfo');
    const user = JSON.parse(EmployeeInfo);
    const [error, setError] = useState(null);


    const getTodayDate = () => {
        const today = new Date();
        return today.toISOString().split('T')[0]; // Format YYYY-MM-DD
    };

    const getCurrentTime = () => {
        const now = new Date();
        return now.toTimeString().split(' ')[0].substring(0, 5); // Format HH:MM
    };

    const [formData, setFormData] = useState({
        employeeId: user.employeeId,
        name: "",
        date: getTodayDate(),
        timeBegin: '',
        timeEnd: ''
    });

    useEffect(() => {
        fetch('http://localhost:8080/api/employees/' + user.employeeId)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setFormData(prevFormData => ({
                    ...prevFormData,
                    name: data.name
                }));
            })
            .catch(error => {
                setError(error);
                console.error('There was a problem with the fetch operation:', error);
            });
    }, [user.employeeId]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevFormData => {
            const updatedData = { ...prevFormData, [name]: value };
            // Validate timeBegin and timeEnd here
            if (name === 'timeBegin' || name === 'timeEnd') {
                validateTimes(updatedData);
            }
            return updatedData;
        });
    };

    const validateTimes = (data) => {
        if (data.timeBegin && data.timeEnd) {
            if (data.timeBegin > data.timeEnd) {
                alert('Time Begin cannot be greater than Time End.');
                setFormData(prevFormData => ({
                    ...prevFormData,
                    timeBegin: '' // Clear timeBegin if invalid
                }));
            } else if (data.timeBegin < getCurrentTime()) {
                alert('Time Begin cannot be earlier than the current time.');
                setFormData(prevFormData => ({
                    ...prevFormData,
                    timeBegin: '' // Clear timeBegin if invalid
                }));
            }
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        // Validate that timeBegin is not smaller than the current time and not greater than timeEnd
        if (formData.timeBegin < getCurrentTime()) {
            alert('Time Begin cannot be earlier than the current time.');
            return;
        }

        if (formData.timeBegin > formData.timeEnd) {
            alert('Time Begin cannot be greater than Time End.');
            return;
        }

        fetch('http://localhost:8081/api/timesheet/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(response => response.text())
        .then(data => {
            console.log('Success:', data);
            alert('TimeSheet saved successfully!');
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to save TimeSheet.');
        });
    };

   

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Employee ID:</label>
                <input
                    type="number"
                    name="employeeId"
                    value={formData.employeeId}
                    onChange={handleChange}
                    required
                    readOnly
                />
            </div>
            <div>
                <label>Name:</label>
                <input
                    type="text"
                    name="name"
                    value={formData.name}
                    onChange={handleChange}
                    required
                />
            </div>
            <div>
                <label>Date:</label>
                <input
                    type="date"
                    name="date"
                    value={formData.date}
                    onChange={handleChange}
                    min={getTodayDate()}
                    required
                />
            </div>
            <div>
                <label>Time Begin:</label>
                <input
                    type="time"
                    name="timeBegin"
                    value={formData.timeBegin}
                    onChange={handleChange}
                    required
                />
            </div>
            <div>
                <label>Time End:</label>
                <input
                    type="time"
                    name="timeEnd"
                    value={formData.timeEnd}
                    onChange={handleChange}
                    required
                />
            </div>
            <button type="submit">Submit</button>
        </form>
    );
}

export default UpdateTimeSheet;






// setName(data.name);
// setFormData(prevFormData => ({
//     ...prevFormData,
//     name: data.name
// }));
