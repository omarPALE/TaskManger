import { useState } from "react";
import axios from "axios";
import "./login.css";
import { useNavigate } from "react-router-dom";
const LogIn = () => {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });
  const navigate = useNavigate();
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      const response = await axios.post("http://localhost:8080/auth/login", {
        email: formData.email,
        password: formData.password,
      });

      console.log("Login successful:", response.data);
      alert("Login successful!"); // Or redirect user to a dashboard
      navigate("/"); // Replace "/dashboard" with the desired route for logged-in users

    } catch (err) {
      console.error("Error during login:", err);
      setError("Invalid email or password. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="sign-in-container">
      <div className="form-box">
        <h2>Welcome Back!</h2>
        <p className="subtext">Sign in to continue managing your tasks</p>
        {error && <p className="error-message">{error}</p>}
        <form onSubmit={handleSubmit}>
          <input
            type="email"
            name="email"
            placeholder="Email"
            value={formData.email}
            onChange={handleChange}
            required
          />
          <input
            type="password"
            name="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
            required
          />
          <button type="submit" disabled={loading}>
            {loading ? "Signing In..." : "Sign In"}
          </button>
        </form>
        <p className="redirect">
          Don&apos;t have an account? <a href="/signup">Sign Up</a>
        </p>
      </div>
    </div>
  );
};

export default LogIn;
