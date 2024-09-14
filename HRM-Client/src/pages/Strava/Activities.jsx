import React from 'react';
import Header from "../../components/common/Header/Header.jsx";
import Footer from "../../components/common/Footer/Footer.jsx";
import StravaActivities from './StravaActivities.jsx';
import "./Activities.scss";

const Activities = () => {
  return (
      <div className="my-profile-container">
        <Header />
        <div className="strava-content">
          <StravaActivities />
        </div>
        <Footer />
      </div>
  );
};

export default Activities;