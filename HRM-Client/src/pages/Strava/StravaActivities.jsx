import React, { useEffect, useState } from 'react';
import { getActivities } from './stravaService';

const StravaActivities = () => {
  const [activities, setActivities] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [activitiesPerPage] = useState(4);

  useEffect(() => {
    const fetchActivities = async () => {
      const userId = localStorage.getItem('userId');
      if (userId) {
        const data = await getActivities(userId);
        setActivities(data);
      }
    };
    fetchActivities();
  }, []);

  // Get current activities
  const indexOfLastActivity = currentPage * activitiesPerPage;
  const indexOfFirstActivity = indexOfLastActivity - activitiesPerPage;
  const currentActivities = activities.slice(indexOfFirstActivity, indexOfLastActivity);

  // Change page
  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <div className="activities-content">
      <h1>My Strava Activities</h1>
      <div className="activities-list">
        {currentActivities.map((activity) => (
          <div key={activity.id} className="activity-card">
            <h2>{activity.name}</h2>
            <p>Distance: {(activity.distance / 1000).toFixed(2)} km</p>
            <p>Moving Time: {Math.floor(activity.moving_time / 60)} minutes</p>
            <p>Elapsed Time: {Math.floor(activity.elapsed_time / 60)} minutes</p>
            <p>Total Elevation Gain: {activity.total_elevation_gain} meters</p>
            <p>Type: {activity.type}</p>
            <p>Sport Type: {activity.sport_type}</p>
          </div>
        ))}
      </div>
      <div className="pagination">
        <button 
          onClick={() => paginate(currentPage - 1)} 
          disabled={currentPage === 1}
        >
          Previous
        </button>
        <button 
          onClick={() => paginate(currentPage + 1)} 
          disabled={indexOfLastActivity >= activities.length}
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default StravaActivities;
