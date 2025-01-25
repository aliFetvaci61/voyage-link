import React, { useState } from 'react';
import api from '../services/axios';

const CreateLocation = ({ onCreate , showToast}) => {
  const [name, setName] = useState('');
  const [country, setCountry] = useState('');
  const [city, setCity] = useState('');
  const [locationCode, setLocationCode] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    const locationData = {
      name,
      country,
      city,
      locationCode,
    };

    try {
      const response = await api.post('/travel-management-service/api/v1/location', locationData);
      if (response.data.success) {
        onCreate(response.data.data); 
        showToast('Location created successfully', 'success');
      }
    } catch (error) {
        console.error("Error creating location", error);
        showToast('Failed to create location', 'error');
    }
  };

  return (
    <div>
      <h2>Create New Location</h2>
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
        <button type="submit">Create Location</button>
      </form>
    </div>
  );
};

export default CreateLocation;
