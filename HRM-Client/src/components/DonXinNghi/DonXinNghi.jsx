// src/ContactForm.js
import React, { useState } from 'react';

function DonXinNghi() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState('');

    // const handleSubmit = (event) => {
    //     event.preventDefault();
    //     alert(`Form submitted!\nName: ${name}\nEmail: ${email}\nMessage: ${message}`);
    //     // Here you can handle the form data, such as sending it to a server
    // };



    const [selecteRestdDay, setSelectedRestDay] = useState('');
    const [selecteWorkdDay, setSelectedWorkDay] = useState('');

    const handleRestDayChange = (event) => {
        setSelectedRestDay(event.target.value);
    };
    const handleWorkDayChange = (event) => {
        setSelectedWorkDay(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        alert(`Selected Day: ${selecteRestdDay}`);
    };

    return (
        <form onSubmit={handleSubmit} style={styles.form}>
            <div style={styles.formGroup}>
                <label htmlFor="name">Name:</label>
                <input
                    type="text"
                    id="name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    style={styles.input}
                    required
                />
            </div>
            <div style={styles.formGroup}>
                <label htmlFor="number">Số điện thoại:</label>
                <input
                    // type="email"
                    id="phoneNumber"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    style={styles.input}
                    required
                />
            </div>

            <div style={styles.formGroup}>
                <label htmlFor="day">Ngày bắt đầu nghỉ:</label>
                <input
                    type="date"
                    id="day"
                    value={selecteRestdDay}
                    onChange={handleRestDayChange}
                    style={styles.input}
                    required
                />
            </div>

            <div style={styles.formGroup}>
                <label htmlFor="day">Ngày đi làm lại:</label>
                <input
                    type="date"
                    id="day"
                    value={selecteWorkdDay}
                    onChange={handleWorkDayChange}
                    style={styles.input}
                    required
                />
            </div>
            
            <div style={styles.formGroup}>
                <label htmlFor="message">Lý do xin nghỉ:</label>
                <textarea
                    id="message"
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    style={styles.textarea}
                    rows="4"
                    required
                />
            </div>
            <button type="submit" style={styles.button}>Submit</button>
        </form>
    );
}

const styles = {
    form: {
        maxWidth: '400px',
        margin: '0 auto',
        padding: '20px',
        borderRadius: '5px',
        backgroundColor: '#f4f4f4',
        boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)',
    },
    formGroup: {
        marginBottom: '15px',
    },
    input: {
        width: '100%',
        padding: '10px',
        fontSize: '14px',
        borderRadius: '4px',
        border: '1px solid #ccc',
        boxSizing: 'border-box',
    },
    textarea: {
        width: '100%',
        padding: '10px',
        fontSize: '14px',
        borderRadius: '4px',
        border: '1px solid #ccc',
        boxSizing: 'border-box',
        resize: 'none',
    },
    button: {
        width: '100%',
        padding: '10px 15px',
        backgroundColor: '#5cb85c',
        color: 'white',
        fontSize: '16px',
        border: 'none',
        borderRadius: '4px',
        cursor: 'pointer',
    }
};

export default DonXinNghi;
