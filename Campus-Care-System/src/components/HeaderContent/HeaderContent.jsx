import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './HeaderContent.css';
import logo from '../../assets/u-ruhuna.png';

const HeaderContent = ({ searchTerm, setSearchTerm }) => {
  const navigate = useNavigate();

  const handleSearch = (e) => {
    e.preventDefault();
    // Already handled by real-time filtering
    console.log('Search term:', searchTerm);
  };

  return (
    <header className="header">
      <div className="header-top">
        <div className="logo">
          <a href="https://www.eng.ruh.ac.lk/" target="_blank" rel="noopener noreferrer">
            <img src={logo} alt="University Logo" />
          </a>
        </div>
        <div className="university-info">
          <h1>University of Ruhuna</h1>
          <h2>Faculty of Engineering</h2>
        </div>
      </div>
      <nav className="navbar">
        <div className="nav-links">
          <Link to="/">Home</Link>
          <Link to="/add-update">Add Update</Link>
          <Link to="/signup">SignUp</Link>
          <Link to="/login">Login</Link>
        </div>
        <form className="search-bar" onSubmit={handleSearch}>
          <input
            type="text"
            placeholder="Search issues..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
          <button type="submit">Search</button>
        </form>
      </nav>
    </header>
  );
};

export default HeaderContent;