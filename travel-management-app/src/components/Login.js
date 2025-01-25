import { useState } from 'react';
import api from '../services/axios'; 
import { useNavigate } from 'react-router-dom';
import Toast from './Toast';  

function Login() {
  const [identificationNumber, setIdentificationNumber] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const [toast, setToast] = useState(null); 


  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post('/auth-service/api/v1/login', {
        identificationNumber,
        password
      });
      localStorage.setItem('token', response.data.data.token); 
      navigate('/locations');
    } catch (err) {
        showToast('Login failed !', 'error');
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
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit">Login</button>
      </form>
    </div>
  );
}

export default Login;
