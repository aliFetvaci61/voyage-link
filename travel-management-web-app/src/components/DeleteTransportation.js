import React from 'react';
import api from '../services/axios';

const DeleteTransportation = ({ transportationId, onDelete, showToast }) => {
  const handleDelete = async () => {
    try {
      const response = await api.delete(`/travel-management-service/api/v1/transportation/${transportationId}`);
      if (response.data.success) {
        onDelete(transportationId); 
        showToast('Transportation deleted successfully', 'success'); 
      }
    } catch (error) {
      console.error('Error deleting transportation', error);
      showToast('Failed to delete transportation', 'error'); 
    }
  };

  return (
    <button onClick={handleDelete}>
      Delete Transportation
    </button>
  );
};

export default DeleteTransportation;
