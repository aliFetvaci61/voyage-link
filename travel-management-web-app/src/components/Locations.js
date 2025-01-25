import React, { useState, useEffect } from 'react';
import api from '../services/axios';
import CreateLocation from './CreateLocation';
import UpdateLocation from './UpdateLocation';
import DeleteLocation from './DeleteLocation';
import Toast from './Toast';  

import './Locations.css';  

const Locations = () => {
  const [locations, setLocations] = useState([]);
  const [selectedLocation, setSelectedLocation] = useState(null);
  const [toast, setToast] = useState(null);  

  useEffect(() => {
    const fetchLocations = async () => {
      try {
        const response = await api.get('/travel-management-service/api/v1/location');
        if (response.data.success) {
          setLocations(response.data.data);
        } 
      } catch (error) {
        console.error("Error fetching locations", error);
      }
    };

    fetchLocations();
  }, []);

  const handleCreate = (newLocation) => {
    setLocations([...locations, newLocation]);
  };

  const handleUpdate = (updatedLocation) => {
    setLocations(locations.map((loc) =>
      loc.id === updatedLocation.id ? updatedLocation : loc
    ));
  };

  const handleDelete = (locationId) => {
    setLocations(locations.filter((loc) => loc.id !== locationId));
  };

  const showToast = (message, type) => {
    setToast({ message, type });
    setTimeout(() => {
      setToast(null);  
    }, 3000);
  };

  return (
    <div className="location-container">
      {toast && <Toast message={toast.message} type={toast.type} />}

      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>City</th>
            <th>Country</th>
            <th>Location Code</th>
            <th>Update Actions</th>
            <th>Delete Actions</th>
          </tr>
        </thead>
        <tbody>
          {locations.map((location) => (
            <tr key={location.id}>
              <td>{location.name}</td>
              <td>{location.city}</td>
              <td>{location.country}</td>
              <td>{location.locationCode}</td>
              <td><button onClick={() => setSelectedLocation(location)}>Edit</button></td>
              <td>
                <DeleteLocation 
                  locationId={location.id} 
                  onDelete={handleDelete} 
                  showToast={showToast} 
                />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
  
      <div className="update-location">
        {selectedLocation ? (
          <UpdateLocation
            locationId={selectedLocation.id}
            currentData={selectedLocation}
            onUpdate={handleUpdate}
            showToast={showToast}
          />
        ) : null}
      </div>
  
      <div className="create-location">
        <CreateLocation onCreate={handleCreate} showToast={showToast} />
      </div>
    </div>
  );
};

export default Locations;
