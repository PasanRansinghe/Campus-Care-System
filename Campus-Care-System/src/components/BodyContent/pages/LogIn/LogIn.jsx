import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './LogIn.css';

const LogIn = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
    rememberMe: false
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === 'checkbox' ? checked : value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      const res = await fetch('http://localhost:8081/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          email: formData.email,
          password: formData.password
        })
      });

      if (res.ok) {
        const data = await res.json();
        console.log('Login successful:', data);
        // Save user info in localStorage if needed
        if (formData.rememberMe) {
          localStorage.setItem('user', JSON.stringify(data));
        } else {
          sessionStorage.setItem('user', JSON.stringify(data));
        }
        navigate('/'); // redirect to home
      } else {
        const errData = await res.json();
        setError(errData.message || 'Login failed');
      }
    } catch (err) {
      console.error('Error during login:', err);
      setError('An error occurred. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-form">
        <h2>Welcome back!</h2>
        <p>Enter your credentials to access your account</p>

        <form onSubmit={handleSubmit}>
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
              placeholder="Enter your password"
              required
            />
          </div>

          <div className="remember-me">
            <input
              type="checkbox"
              id="rememberMe"
              name="rememberMe"
              checked={formData.rememberMe}
              onChange={handleChange}
            />
            <label htmlFor="rememberMe">Remember me for 30 days</label>
          </div>

          {error && <p className="error-msg">{error}</p>}

          <button type="submit" className="login-btn" disabled={loading}>
            {loading ? 'Logging in...' : 'Login'}
          </button>
        </form>

        <div className="social-login">
          <button className="google-btn">Sign in with Google</button>
          <button className="apple-btn">Sign in with Apple</button>
        </div>

        <div className="signup-link">
          <p>Don't have an account? <Link to="/signup">Sign Up</Link></p>
        </div>
      </div>

      <div className="login-image">
        <div className="placeholder-image">
          <div className="placeholder-content">
            <h2>Welcome To<br />Ruhuna...!</h2>
            <p>Campus Care System</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LogIn;
