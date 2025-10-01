import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './pages/Home/Home';
import AddUpdate from './pages/AddUpdate/AddUpdate';
import LogIn from './pages/LogIn/LogIn';
import SignUp from './pages/SignUp/SignUp';
import './BodyContent.css';

const BodyContent = ({ searchTerm }) => {
  return (
    <div className="body-content">
      <div className="content-container">
        <Routes>
          <Route path="/" element={<Home searchTerm={searchTerm} />} />
          <Route path="/add-update" element={<AddUpdate />} />
          <Route path="/login" element={<LogIn />} />
          <Route path="/signup" element={<SignUp />} />
        </Routes>
      </div>
    </div>
  );
};

export default BodyContent;