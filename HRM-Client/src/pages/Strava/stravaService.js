import axios from 'axios';

const clientId = '127861';
const redirectUri = 'http://localhost:5173/callback';
const scope = 'activity:read';

export const getAuthorizationUrl = () => {
  return `https://www.strava.com/api/v3/oauth/authorize?client_id=${clientId}&response_type=code&redirect_uri=${encodeURIComponent(redirectUri)}&scope=${scope}`;
};

export const exchangeToken = async (code) => {
  try {
    console.log('Code:', code);
    const response = await axios.post('http://localhost:8083/api/strava/exchange_token', { code });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getActivities = async (accessToken) => {
  try {
    const response = await axios.get('https://www.strava.com/api/v3/clubs/1257078/activities?per_page=30', {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching activities:', error);
    throw error;
  }
};