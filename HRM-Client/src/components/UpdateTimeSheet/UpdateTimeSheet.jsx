import React, { useState, useEffect } from 'react';

function UpdateTimeSheetForm() {
    const EmployeeInfo = localStorage.getItem('userInfo');
    const user = JSON.parse(EmployeeInfo);
    const [name, setName] = useState('');
    const [error, setError] = useState(null);

    useEffect(() => {
        fetch('http://localhost:8080/api/employees/1')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setName(data.name);
            })
            .catch(error => {
                setError(error);
                console.error('There was a problem with the fetch operation:', error);
            });
    }, []);

    const [formData, setFormData] = useState({
        employeeId: user.employeeId,
        name: name,
        date: '',
        timeBegin: '',
        timeEnd: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("formData: ", formData);

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

    const getTodayDate = () => {
        const today = new Date();
        return today.toISOString().split('T')[0]; // Format YYYY-MM-DD
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
                />
            </div>
            <div>
                <label>Name:</label>
                <input
                    type="text"
                    name="name"
                    value={name}
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

export default UpdateTimeSheetForm;
