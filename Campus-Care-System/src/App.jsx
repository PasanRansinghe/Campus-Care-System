import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HeaderContent from './components/HeaderContent/HeaderContent';
import FooterContent from './components/FooterContent/FooterContent';
import Home from './components/BodyContent/pages/Home/Home';
import AddUpdate from './components/BodyContent/pages/AddUpdate/AddUpdate';
import LogIn from './components/BodyContent/pages/LogIn/LogIn';
import SignUp from './components/BodyContent/pages/SignUp/SignUp';
import './App.css';

function App() {
  const [searchTerm, setSearchTerm] = useState('');

  return (
    <Router>
      <div className="App">
        <HeaderContent searchTerm={searchTerm} setSearchTerm={setSearchTerm} />
        <Routes>
          <Route path="/" element={<Home searchTerm={searchTerm} />} />
          <Route path="/add-update" element={<AddUpdate />} />
          <Route path="/login" element={<LogIn />} />
          <Route path="/signup" element={<SignUp />} />
        </Routes>
        <FooterContent />
      </div>
    </Router>
  );
}

export default App;