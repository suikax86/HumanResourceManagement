import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getAuthorizationUrl } from './stravaService';

const StravaAuth = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const checkAuthAndRedirect = () => {
            const userId = localStorage.getItem('userId');
            if (userId) {
                navigate('/strava');
            } else {
                window.location.href = getAuthorizationUrl();
            }
        };

        checkAuthAndRedirect();
    }, [navigate]);

    return <div>Checking Strava authorization...</div>;
};

export default StravaAuth;