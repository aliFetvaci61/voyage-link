import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import './Sidebar.css'; 

function Sidebar() {
  const location = useLocation(); 

  return (
    <div className="sidebar">
      <ul>
        <li>
          <Link
            to="/locations"
            className={location.pathname === '/locations' ? 'active' : ''}
          >
            Locations
          </Link>
        </li>
        <li>
          <Link
            to="/transportations"
            className={location.pathname === '/transportations' ? 'active' : ''}
          >
            Transportations
          </Link>
        </li>
        <li>
          <Link
            to="/routes"
            className={location.pathname === '/routes' ? 'active' : ''}
          >
            Routes
          </Link>
        </li>
      </ul>
    </div>
  );
}

export default Sidebar;
