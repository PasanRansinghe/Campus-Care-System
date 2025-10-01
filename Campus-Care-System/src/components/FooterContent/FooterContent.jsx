import React from 'react';
import './FooterContent.css';

const FooterContent = () => {
  return (
    <footer className="footer">
      <div className="footer-top">
        <div className="footer-links">
          <a href="#">ENG</a>
          <a href="#">BMS</a>
          <a href="#">HIT</a>
        </div>
        <div className="footer-links">
          <a href="#">Terms & Conditions</a>
          <a href="#">Privacy Policy</a>
        </div>
      </div>
      <div className="footer-bottom">
        <div className="contact-info">
          <p>Contact us</p>
          <p>Faculty of Engineering, Hapugala, Galle, Sri Lanka</p>
          <p>Phone: +944122457656; Email: webmaster@eng.ruh.ac.lk</p>
        </div>
        <div className="copyright">
          <p>Copyright © 2024 Logoipsum. All rights reserved.</p>
        </div>
      </div>
    </footer>
  );
};

export default FooterContent;