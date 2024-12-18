import { Link } from "react-router-dom";
import "./home.css";

const Home = () => {
  return (
    <div className="home-container">
      <nav className="navbar">
        <div className="logo">
          <Link to="/">TaskManager</Link>
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
