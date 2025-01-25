import { useState } from 'react';
import api from '../services/axios';
import Toast from './Toast';  

function Register() {
  const [identificationNumber, setIdentificationNumber] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [password, setPassword] = useState('');
  const [toast, setToast] = useState(null); 

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post('/auth-service/api/v1/register', {
        identificationNumber,
        firstName,
        lastName,
        password
      });
      showToast('Registration successful', 'success');
    } catch (err) {
      showToast('Registration failed !', 'error');
    }
  };

  const showToast = (message, type) => {
    setToast({ message, type });
    setTimeout(() => {
      setToast(null);  
    }, 3000);
  };

  return (
    <div className="form-container">
      {toast && <Toast message={toast.message} type={toast.type} />}  
      
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Identification Number"
          value={identificationNumber}
          onChange={(e) => setIdentificationNumber(e.target.value)}
        />
        <input
          type="text"
          placeholder="First Name"
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
        />
        <input
          type="text"
          placeholder="Last Name"
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit">Register</button>
      </form>
    </div>
  );
}

export default Register;
