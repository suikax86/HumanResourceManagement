import React, { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { exchangeToken } from "./stravaService";

const Callback = () => {
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchToken = async () => {
      const params = new URLSearchParams(location.search);
      const code = params.get("code");
      if (code) {
        try {
          const tokenData = await exchangeToken(code);
          localStorage.setItem("userId", tokenData.userId);
          navigate("/strava");
        } catch (error) {
          console.error("Error during token exchange:", error);
          navigate("/");
        }
      } else {
        navigate("/");
      }
    };
    fetchToken();
  }, [location, navigate]);

  return <div>Processing Strava authorization...</div>;
};

export default Callback;