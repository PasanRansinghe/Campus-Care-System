import React, { useState, useEffect } from 'react';
import './Home.css';

const Home = ({ searchTerm }) => {
  const [cards, setCards] = useState([]);
  const [loading, setLoading] = useState(true);

  // Fetch issues from backend
  useEffect(() => {
    fetch("http://localhost:8081/api/issues")
      .then((res) => res.json())
      .then((data) => {
        setCards(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching issues:", err);
        setLoading(false);
      });
  }, []);

  // Handle delete
  const handleDelete = async (id) => {
    try {
      const res = await fetch(`http://localhost:8081/api/issues/${id}`, {
        method: "DELETE",
      });

      if (res.ok) {
        setCards(cards.filter((card) => card.id !== id));
      } else {
        console.error("Failed to delete issue");
      }
    } catch (err) {
      console.error("Error deleting issue:", err);
    }
  };

  // Filter cards based on search term
  const filteredCards = cards.filter((card) => {
    if (!searchTerm) return true;

    const searchLower = searchTerm.toLowerCase();
    return (
      card.title.toLowerCase().includes(searchLower) ||
      card.description.toLowerCase().includes(searchLower) ||
      card.location.toLowerCase().includes(searchLower) ||
      card.status.toLowerCase().includes(searchLower) ||
      card.category.toLowerCase().includes(searchLower)
    );
  });

  // Function to get status icon based on status
  const getStatusIcon = (status) => {
    switch (status.toLowerCase()) {
      case "pending":
        return "⏳";
      case "finished":
        return "✅";
      default:
        return "";
    }
  };

  if (loading) {
    return <div className="home"><p>Loading issues...</p></div>;
  }

  return (
    <div className="home">
      <div className="home-header">
        <h1>HOME</h1>
        {searchTerm && (
          <p className="search-results-info">
            Showing {filteredCards.length} result
            {filteredCards.length !== 1 ? "s" : ""} for "{searchTerm}"
          </p>
        )}
      </div>
      <div className="cards-container">
        {filteredCards.length > 0 ? (
          filteredCards.map((card) => (
            <div key={card.id} className="card">
              <div className="card-header">
                <h3>{card.title}</h3>
                <div className="card-badges">
                  <span className={`type-badge ${card.category?.toLowerCase()}`}>
                    {card.category}
                  </span>
                  <span className={`status-badge ${card.status?.toLowerCase()}`}>
                    {getStatusIcon(card.status)} {card.status}
                  </span>
                </div>
              </div>
              <div className="card-content">
                <p><strong>Description:</strong> {card.description}</p>
                <p><strong>Location:</strong> {card.location}</p>
                <p><strong>Date:</strong> {card.date}</p>
              </div>
              <button
                className="delete-btn"
                onClick={() => handleDelete(card.id)}
              >
                Delete
              </button>
            </div>
          ))
        ) : (
          <div className="no-results">
            <p>No issues found {searchTerm && `matching "${searchTerm}"`}</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default Home;
