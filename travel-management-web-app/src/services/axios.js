import axios from 'axios';


const api = axios.create({
    baseURL: process.env.API_BASE_URL || 'http://localhost:6161', 
  });

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');  
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`; 
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response && error.response.status === 401) {
      window.location.href = '/login';  
    }
    return Promise.reject(error);
  }
);

export default api;
