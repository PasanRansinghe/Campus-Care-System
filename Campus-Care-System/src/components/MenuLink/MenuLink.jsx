import React from 'react';
import { Link } from 'react-router-dom';

const MenuLink = ({ to, label }) => {
  return (
    <Link to={to} className="nav-link">
      {label}
    </Link>
  );
};

export default MenuLink;