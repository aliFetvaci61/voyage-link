import React, { useState, useEffect } from 'react';
import api from '../services/axios';
import CreateTransportation from './CreateTransportation';
import UpdateTransportation from './UpdateTransportation';
import DeleteTransportation from './DeleteTransportation';
import Toast from './Toast';  

import './Transportation.css';

const Transportation = () => {
  const [transportations, setTransportations] = useState([]);
  const [locations, setLocations] = useState([]);
  const [selectedTransportation, setSelectedTransportation] = useState(null);
  const [toast, setToast] = useState(null);  
  

  useEffect(() => {
    const fetchData = async () => {
      try {
        const locationResponse = await api.get('/travel-management-service/api/v1/location');
        if (locationResponse.data.success) {
          setLocations(locationResponse.data.data);
        } else {
          showToast('No locations found', 'error');
        }

        const transportationResponse = await api.get('/travel-management-service/api/v1/transportation');
        if (transportationResponse.data.success) {
          setTransportations(transportationResponse.data.data);
        } else {
          showToast('No transportations found', 'error');
        }
      } catch (error) {
        console.error('Error fetching data', error);
      }
    };

    fetchData();
  }, []);

  const handleCreate = (newTransportation) => {
    setTransportations([...transportations, newTransportation]);
  };

  const handleUpdate = (updatedTransportation) => {
    setTransportations(
      transportations.map((transportation) =>
        transportation.id === updatedTransportation.id ? updatedTransportation : transportation
      )
    );
  };

  const handleDelete = (transportationId) => {
    setTransportations(transportations.filter((transportation) => transportation.id !== transportationId));
  };


  const showToast = (message, type) => {
    setToast({ message, type });
    setTimeout(() => {
    setToast(null); 
    }, 3000);
  };

  return (
    <div className="transportation-container">

      {toast && <Toast message={toast.message} type={toast.type} />}

      <table>
        <thead>
          <tr>
            <th>Transportation Type</th>
            <th>From</th>
            <th>To</th>
            <th>Update Actions</th>
            <th>Delete Actions</th>
          </tr>
        </thead>
        <tbody>
          {transportations.map((transportation) => (
            <tr key={transportation.id}>
              <td>{transportation.transportationType}</td>
              <td>{transportation.originLocation.name}</td>
              <td>{transportation.destinationLocation.name}</td>
              <td><button onClick={() => setSelectedTransportation(transportation)}>Edit</button></td>
              <td><DeleteTransportation transportationId={transportation.id} onDelete={handleDelete} showToast={showToast}/>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
  
      
  
      <div className="update-transportation">
      {selectedTransportation && (
        <UpdateTransportation
          transportationId={selectedTransportation.id}
          currentData={selectedTransportation}
          locations={locations}
          onUpdate={handleUpdate}
          showToast={showToast}
        />
      )}
      </div>

      <div className="create-transportation">
        <CreateTransportation  locations={locations} onCreate={handleCreate} showToast={showToast} />
      </div>
    </div>
  );
  
};

export default Transportation;
