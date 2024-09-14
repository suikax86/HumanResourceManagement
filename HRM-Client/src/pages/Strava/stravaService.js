import axios from "axios";

const clientId = "134585";
const redirectUri = "http://localhost:5173/callback";
const scope = "activity:read";

export const getAuthorizationUrl = () => {
  return `https://www.strava.com/api/v3/oauth/authorize?client_id=${clientId}&response_type=code&redirect_uri=${encodeURIComponent(
      redirectUri
  )}&scope=${scope}`;
};

export const exchangeToken = async (code) => {
  const response = await axios.post(
      "http://localhost:8080/api/activities/strava/exchange_token",
      { code }
  );
  return response.data;
};

export const getActivities = async (userId) => {
  try {
    const response = await axios.get(
        `http://localhost:8080/api/activities/strava/activities?userId=${userId}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching activities:", error);
    throw error;
  }
};