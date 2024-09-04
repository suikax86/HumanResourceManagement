import React, { useEffect, useState } from 'react';
import { getActivities } from './stravaService';

const Activities = () => {
  const [activities, setActivities] = useState([]);

  useEffect(() => {
    const fetchActivities = async () => {
      const accessToken = localStorage.getItem('access_token');
      if (accessToken) {
        const data = await getActivities(accessToken);
        setActivities(data);
      }
    };
    fetchActivities();
  }, []);

  return (
    <div>
      <h1>Activities</h1>
      <div>
        {activities.map((activity) => (
          <div key={activity.id}>
            <h2>{activity.name}</h2>
            <p>Distance: {activity.distance} meters</p>
            <p>Moving Time: {activity.moving_time} seconds</p>
            <p>Elapsed Time: {activity.elapsed_time} seconds</p>
            <p>Total Elevation Gain: {activity.total_elevation_gain} meters</p>
            <p>Type: {activity.type}</p>
            <p>Sport Type: {activity.sport_type}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Activities;