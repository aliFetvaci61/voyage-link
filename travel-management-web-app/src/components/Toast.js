import React from 'react';
import './Toast.css';

const Toast = ({ message, type }) => {
  const toastClass = type === 'success' ? 'toast success' : 'toast error';

  return (
    <div className={toastClass}>
      <p>{message}</p>
    </div>
  );
};

export default Toast;
