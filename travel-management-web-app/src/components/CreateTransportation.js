import React, { useState, useEffect } from 'react';
import api from '../services/axios';


function CreateTransportation({onCreate, showToast}) {
  const [locations, setLocations] = useState([]);
  const [originLocationId, setOriginLocationId] = useState('');
  const [destinationLocationId, setDestinationLocationId] = useState('');
  const [transportationType, setTransportationType] = useState('FLIGHT');
  const [operatingDays, setOperatingDays] = useState([]);

  useEffect(() => {
    api.get('/travel-management-service/api/v1/location')
      .then((response) => {
        if (response.data && response.data.data) {
          setLocations(response.data.data);
        } else {
          console.error('Locations data is invalid');
        }
      })
      .catch((error) => {
        console.error('Error fetching locations:', error);
      });
  }, []);


  const handleOperatingDaysChange = (e) => {
    const selectedOptions = Array.from(e.target.selectedOptions, option => option.value);
    setOperatingDays(selectedOptions);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const data = {
      originLocationId,
      destinationLocationId,
      transportationType,
      operatingDays: operatingDays.map(day => parseInt(day)), 
    };

    api.post('/travel-management-service/api/v1/transportation', data)
      .then(response => {
        if (response.data.success) {
          onCreate(response.data.data);
          showToast('Transportation created successfully', 'success');
        } else {
          showToast('Failed to create transportation', 'error');
        }
      })
      .catch(error => {
        console.error('Error creating transportation:', error);
        showToast('An error occurred while creating the transportation', 'error');
      });
  };

  return (
    <div>
      <h2>Create Transportation</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="originLocationId">Origin Location:</label>
          <select
            id="originLocationId"
            value={originLocationId}
            onChange={(e) => setOriginLocationId(e.target.value)}
            required
          >
            <option value="">Select Origin Location</option>
            {locations.map((location) => (
              <option key={location.id} value={location.id}>
                {location.name} - {location.city}, {location.country}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="destinationLocationId">Destination Location:</label>
          <select
            id="destinationLocationId"
            value={destinationLocationId}
            onChange={(e) => setDestinationLocationId(e.target.value)}
            required
          >
            <option value="">Select Destination Location</option>
            {locations.map((location) => (
              <option key={location.id} value={location.id}>
                {location.name} - {location.city}, {location.country}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="transportationType">Transportation Type:</label>
          <select
            id="transportationType"
            value={transportationType}
            onChange={(e) => setTransportationType(e.target.value)}
            required
          >
            <option value="FLIGHT">FLIGHT</option>
            <option value="BUS">BUS</option>
            <option value="SUBWAY">SUBWAY</option>
            <option value="UBER">UBER</option>
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="operatingDays">Operating Days:</label>
          <select
            id="operatingDays"
            multiple
            value={operatingDays}
            onChange={handleOperatingDaysChange}
            required
          >
            <option value={1}>Monday</option>
            <option value={2}>Tuesday</option>
            <option value={3}>Wednesday</option>
            <option value={4}>Thursday</option>
            <option value={5}>Friday</option>
            <option value={6}>Saturday</option>
            <option value={7}>Sunday</option>
          </select>
        </div>

        <button type="submit">Create Transportation</button>
      </form>
    </div>
  );
}

export default CreateTransportation;
