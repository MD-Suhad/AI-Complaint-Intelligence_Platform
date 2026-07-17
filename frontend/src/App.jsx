import { useState } from 'react';
import './App.css';

function App() {
  const [message, setMessage] = useState('Welcome to the AI Complaint Intelligence frontend!');

  return (
    <div className="app-shell">
      <header>
        <h1>AI Complaint Intelligence</h1>
        <p>{message}</p>
      </header>
      <section className="card-grid">
        <div className="card">
          <h2>Complaint dashboard</h2>
          <p>Build your microservice UI here: analytics, categories, sentiment, and more.</p>
        </div>
        <div className="card">
          <h2>Service architecture</h2>
          <p>Keep frontend separate from backend for clean microservices.</p>
        </div>
      </div>
    </div>
  );
}

export default App;
