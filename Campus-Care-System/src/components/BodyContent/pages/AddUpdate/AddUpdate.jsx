import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './AddUpdate.css';

const AddUpdate = () => {
  const [formData, setFormData] = useState({
    title: '',
    category: '',
    description: '',
    location: '',
    date: '',
    status: 'Pending',
    image: null
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleImageChange = (e) => {
    setFormData({
      ...formData,
      image: e.target.files[0]
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      let res;

      if (formData.image) {
        // Use FormData if image is present
        const form = new FormData();
        form.append("title", formData.title);
        form.append("category", formData.category);
        form.append("description", formData.description);
        form.append("location", formData.location);
        form.append("date", formData.date);
        form.append("status", formData.status);
        form.append("image", formData.image);

        res = await fetch("http://localhost:8081/api/issues", {
          method: "POST",
          body: form
        });
      } else {
        // Use JSON if no image
        res = await fetch("http://localhost:8081/api/issues", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            title: formData.title,
            category: formData.category,
            description: formData.description,
            location: formData.location,
            date: formData.date,
            status: formData.status
          })
        });
      }

      if (res.ok) {
        const newIssue = await res.json();
        console.log("Issue added:", newIssue);
        navigate("/"); // ✅ redirect to home
      } else {
        console.error("Failed to add issue");
      }
    } catch (err) {
      console.error("Error adding issue:", err);
    }
  };

  return (
    <div className="add-update">
      <h2>Add New Issue</h2>
      <form onSubmit={handleSubmit} className="issue-form">
        <div className="form-group">
          <label htmlFor="title">Issue Title</label>
          <input
            type="text"
            id="title"
            name="title"
            value={formData.title}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="category">Category</label>
          <select
            id="category"
            name="category"
            value={formData.category}
            onChange={handleChange}
            required
          >
            <option value="">Select Category</option>
            <option value="Broken">Broken</option>
            <option value="Needed">Needed</option>
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="status">Status</label>
          <select
            id="status"
            name="status"
            value={formData.status}
            onChange={handleChange}
            required
          >
            <option value="Pending">Pending</option>
            <option value="Finished">Finished</option>
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="description">Description</label>
          <textarea
            id="description"
            name="description"
            value={formData.description}
            onChange={handleChange}
            required
          ></textarea>
        </div>

        <div className="form-group">
          <label htmlFor="location">Location</label>
          <input
            type="text"
            id="location"
            name="location"
            value={formData.location}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="date">Date</label>
          <input
            type="date"
            id="date"
            name="date"
            value={formData.date}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="image">Upload Image (optional)</label>
          <input
            type="file"
            id="image"
            name="image"
            onChange={handleImageChange}
            accept="image/*"
          />
        </div>

        <button type="submit" className="submit-btn">Add Issue</button>
      </form>
    </div>
  );
};

export default AddUpdate;