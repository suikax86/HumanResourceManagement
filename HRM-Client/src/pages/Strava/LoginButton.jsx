// src/pages/Strava/LoginButton.jsx
import React from 'react';
import { getAuthorizationUrl } from './stravaService';

const LoginButton = () => {
  const handleLogin = () => {
    window.location.href = getAuthorizationUrl();
  };

  return <button onClick={handleLogin}>Login with Strava</button>;
};

export default LoginButton;