import { Link } from "react-router-dom";
import "./home.css";

const Home = () => {
  return (
    <div className="home-container">
      <nav className="navbar">
        <div className="logo">
          <Link to="/">TaskManager</Link>
        </div>
        <div className="nav-links">
          <Link to="/signin" className="nav-link">
            Sign In
          </Link>
          <Link to="/signup" className="nav-link">
            Sign Up
          </Link>
          <Link to="/task" className="nav-link">
            Task
          </Link>
        </div>
      </nav>
      <div className="home-content">
        <h1>Welcome to TaskManager</h1>
        <p>
          Effortlessly organize and track your tasks. Start your journey today!
        </p>
        <div className="cta-buttons">
          <Link to="/signup" className="cta-btn">
            Get Started
          </Link>
          <Link to="/signin" className="cta-btn-secondary">
            Sign In
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Home;
