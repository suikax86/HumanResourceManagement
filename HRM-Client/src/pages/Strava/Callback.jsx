import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { exchangeToken } from './stravaService';

const Callback = () => {
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchToken = async () => {
      const params = new URLSearchParams(location.search);
      const code = params.get('code');
      console.log('Authorization code:', code); // Debug log
      if (code) {
        try {
          const tokenData = await exchangeToken(code);
          console.log('Token data:', tokenData); // Debug log
          localStorage.setItem('access_token', tokenData.access_token);
          navigate('/activities');
        } catch (error) {
          console.error('Error during token exchange:', error); // Debug log
        }
      }
    };
    fetchToken();
  }, [location, navigate]);

  return <div>Loading...</div>;
};

export default Callback;