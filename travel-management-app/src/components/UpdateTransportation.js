import React, { useState, useEffect } from 'react';
import api from '../services/axios';

const TransportationTypeEnum = {
  FLIGHT: "FLIGHT",
  BUS: "BUS",
  SUBWAY: "SUBWAY",
  UBER: "UBER"
};

const UpdateTransportation = ({ transportationId, onUpdate, currentData, locations, showToast }) => {
  const [originLocationId, setOriginLocationId] = useState(currentData?.originLocation.id || '');
  const [destinationLocationId, setDestinationLocationId] = useState(currentData?.destinationLocation.id || '');
  const [transportationType, setTransportationType] = useState(currentData?.transportationType || '');  
  const [operatingDays, setOperatingDays] = useState(currentData?.operatingDays || []);

  const handleOperatingDaysChange = (event) => {
    const { options } = event.target;
    const selectedDays = [];
    for (let i = 0; i < options.length; i++) {
      if (options[i].selected) {
        selectedDays.push(parseInt(options[i].value)); 
      }
    }
    setOperatingDays(selectedDays);
  };

  useEffect(() => {
    if (transportationId && currentData) {
      setOriginLocationId(currentData.originLocation.id);
      setDestinationLocationId(currentData.destinationLocation.id);
      setTransportationType(currentData.transportationType);  
      setOperatingDays(currentData.operatingDays || []);
    }
  }, [transportationId, currentData]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const updatedTransportationData = {
      originLocationId,
      destinationLocationId,
      transportationType,
      operatingDays,
    };

    try {
      const response = await api.put(`/travel-management-service/api/v1/transportation/${transportationId}`, updatedTransportationData);
      if (response.data.success) {
        onUpdate(response.data.data); 
        showToast('Transportation updated successfully', 'success');
      }
    } catch (error) {
      console.error('Error updating transportation', error);
      showToast('Failed to update transportation', 'error');
    }
  };

  return (
    <div>
      <h2>Update Transportation</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Origin Location:</label>
          <select
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

        <div>
          <label>Destination Location:</label>
          <select
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

        <div>
          <label>Transportation Type:</label>
          <select
            value={transportationType}
            onChange={(e) => setTransportationType(e.target.value)} 
            required
          >
            <option value={TransportationTypeEnum.FLIGHT}>FLIGHT</option>
            <option value={TransportationTypeEnum.BUS}>BUS</option>
            <option value={TransportationTypeEnum.SUBWAY}>SUBWAY</option>
            <option value={TransportationTypeEnum.UBER}>UBER</option>
          </select>
        </div>

        <div>
          <label>Operating Days:</label>
          <select
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

        <button type="submit">Update Transportation</button>
      </form>
    </div>
  );
};

export default UpdateTransportation;
