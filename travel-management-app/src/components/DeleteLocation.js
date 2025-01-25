import React from 'react';
import api from '../services/axios';

const DeleteLocation = ({ locationId, onDelete, showToast }) => {
  const handleDelete = async () => {
    try {
      const response = await api.delete(`/travel-management-service/api/v1/location/${locationId}`);
      if (response.data.success) {
        onDelete(locationId);  
        showToast('Location deleted successfully', 'success');  
      }
    } catch (error) {
      console.error("Error deleting location", error);
      showToast('Failed to delete location', 'error');  
    }
  };

  return (
    <button onClick={handleDelete}>
      Delete
    </button>
  );
};

export default DeleteLocation;
