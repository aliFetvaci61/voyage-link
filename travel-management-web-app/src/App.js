import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import Sidebar from './components/Sidebar';
import Login from './components/Login';
import Register from './components/Register';
import Locations from './components/Locations';
import Transportations from './components/Transportations';
import RoutesPage from './components/Routes';
import Copyright from './components/Copyright'; 



import './App.css';

function App() {
  return (
    <Router>
      <div className="app-container">
        <Header />
        <div className="main-container">
          <Sidebar />
          <div className="content-container">
            <Routes>
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route path="/locations" element={<Locations />} />
              <Route path="/transportations" element={<Transportations />} />
              <Route path="/routes" element={<RoutesPage />} />
            </Routes>
          </div>
        </div>
      </div>
      <Copyright />
    </Router>
  );
}

export default App;

