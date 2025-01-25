import React, { useState, useEffect } from 'react';
import api from '../services/axios';

const UpdateLocation = ({ locationId, onUpdate, currentData , showToast}) => {
  const [name, setName] = useState(currentData.name);
  const [country, setCountry] = useState(currentData.country);
  const [city, setCity] = useState(currentData.city);
  const [locationCode, setLocationCode] = useState(currentData.locationCode);

  useEffect(() => {
    if (locationId && currentData) {
      setName(currentData.name);
      setCountry(currentData.country);
      setCity(currentData.city);
      setLocationCode(currentData.locationCode);
    }
  }, [locationId, currentData]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const updatedLocationData = {
      name,
      country,
      city,
      locationCode,
    };

    try {
      const response = await api.put(`/travel-management-service/api/v1/location/${locationId}`, updatedLocationData);
      if (response.data.success) {
        onUpdate(response.data.data); 
        showToast('Location updated successfully', 'success');
      }
    } catch (error) {
      console.error("Error updating location", error);
      showToast('Failed to delete location', 'error');
    }
  };

  return (
    <div>
      <h2>Update Location</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Name:</label>
          <input type="text" value={name} onChange={(e) => setName(e.target.value)} required />
        </div>
        <div>
          <label>Country:</label>
          <input type="text" value={country} onChange={(e) => setCountry(e.target.value)} required />
        </div>
        <div>
          <label>City:</label>
          <input type="text" value={city} onChange={(e) => setCity(e.target.value)} required />
        </div>
        <div>
          <label>Location Code:</label>
          <input type="text" value={locationCode} onChange={(e) => setLocationCode(e.target.value)} required />
        </div>
        <button type="submit">Update Location</button>
      </form>
    </div>
  );
};

export default UpdateLocation;
