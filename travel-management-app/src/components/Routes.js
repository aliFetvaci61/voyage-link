import React, { useState, useEffect } from 'react';
import api from '../services/axios';
import './Routes.css';  

function Routes() {
  const [locations, setLocations] = useState([]); 
  const [selectedOrigin, setSelectedOrigin] = useState(''); 
  const [selectedDestination, setSelectedDestination] = useState(''); 
  const [selectedDate, setSelectedDate] = useState(''); 
  const [routes, setRoutes] = useState([]); 
  const [loading, setLoading] = useState(false); 

  useEffect(() => {
    api.get('/travel-management-service/api/v1/location')
      .then(response => {
        if (response.data.success) {
          setLocations(response.data.data);
        } else {
          console.error("Locations alınamadı");
        }
      })
      .catch(error => {
        console.error("API hatası:", error);
      });
  }, []); 

  const fetchRoutes = () => {
    if (!selectedOrigin || !selectedDestination || !selectedDate) {
      alert("Lütfen tüm alanları doldurun: Origin, Destination ve Tarih!");
      return;
    }

    setLoading(true);
    const url = `/travel-management-service/api/v1/routes?origin=${selectedOrigin}&destination=${selectedDestination}&date=${selectedDate}`;

    api.get(url)
      .then(response => {
        if (response.data.success) {
          setRoutes(response.data.data);
        } else {
          console.error("Rotalar alınamadı");
        }
      })
      .catch(error => {
        console.error("API hatası:", error);
      })
      .finally(() => setLoading(false));
  };

  const renderRoutes = () => {
    return routes.map((routePath, pathIndex) => {
      const locations = routePath.map(route => ({
        origin: route.originLocation.name,
        destination: route.destinationLocation.name,
        transportationType: route.transportationType
      }));

      const routeString = locations.reduce((acc, curr, index) => {
        if (index === 0) {
          acc = curr.origin + ` → (` + curr.transportationType + `) → ` + curr.destination;
        } else {
          acc += ` → (` + curr.transportationType + `) → ${curr.destination}`;
        }
        return acc;
      }, "");

      return (
        <div key={pathIndex} style={{ marginBottom: '20px' }}>
          <span>{routeString}</span>
        </div>
      );
    });
  };

  return (
    <div className="routes-container">
      {}
      <div className="route-form">
        {}
        <div>
          <label style={{ fontWeight: 'bold', marginBottom: '10px' }}>Origin: </label>
          <select onChange={(e) => setSelectedOrigin(e.target.value)} value={selectedOrigin}>
            <option value="">Select Origin</option>
            {locations.map((location) => (
              <option key={location.id} value={location.locationCode}>
                {location.name} ({location.city}, {location.country})
              </option>
            ))}
          </select>
        </div>
  
        {}
        <div>
          <label style={{ fontWeight: 'bold', marginBottom: '10px'}}>Destination: </label>
          <select onChange={(e) => setSelectedDestination(e.target.value)} value={selectedDestination}>
            <option value="">Select Destination</option>
            {locations.map((location) => (
              <option key={location.id} value={location.locationCode}>
                {location.name} ({location.city}, {location.country})
              </option>
            ))}
          </select>
        </div>
  
        {}
        <div>
          <label style={{ fontWeight: 'bold', marginBottom: '10px' }}>Select Date: </label>
          <input type="date" onChange={(e) => setSelectedDate(e.target.value)} value={selectedDate} />
        </div>

        {}
        <button onClick={fetchRoutes} disabled={loading}>
            {loading ? 'Loading...' : 'Fetch Routes'}
        </button>

      </div>
  
      
  
      {}
      <div style={{ marginTop: '20px' }}>
        {routes.length > 0 ? renderRoutes() : <p>No routes found.</p>}
      </div>
    </div>
  );
}

export default Routes;
