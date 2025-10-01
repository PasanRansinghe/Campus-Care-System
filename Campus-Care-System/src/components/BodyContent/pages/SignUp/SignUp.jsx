import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './SignUp.css';

const SignUp = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: ''
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      const res = await fetch('http://localhost:8081/api/auth/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
      });

      if (res.ok) {
        const data = await res.json();
        console.log('Signup successful:', data);
        navigate('/login'); // Redirect to login page
      } else {
        const errData = await res.json();
        setError(errData.message || 'Signup failed');
      }
    } catch (err) {
      console.error('Error during signup:', err);
      setError('An error occurred. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="signup-container">
      <div className="signup-form">
        <h2>Get Started Now</h2>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="name">Name</label>
            <input
              type="text"
              id="name"
              name="name"
              value={formData.name}
              onChange={handleChange}
              placeholder="Enter your name"
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="email">Email address</label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="Enter your email"
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              placeholder="Create a password"
              required
            />
          </div>

          {error && <p className="error-msg">{error}</p>}

          <button type="submit" className="signup-btn" disabled={loading}>
            {loading ? 'Signing Up...' : 'Sign Up'}
          </button>
        </form>

        <div className="social-signup">
          <button className="google-btn">Sign up with Google</button>
          <button className="apple-btn">Sign up with Apple</button>
        </div>

        <div className="login-link">
          <p>Already have an account? <Link to="/login">Sign In</Link></p>
        </div>
      </div>

      <div className="signup-image">
        <div className="image-placeholder">
          <h2>Welcome To<br />Ruhuna..!</h2>
          <p>Faculty of Engineering</p>
        </div>
      </div>
    </div>
  );
};

export default SignUp;
