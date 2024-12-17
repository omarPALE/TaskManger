import { useState } from "react";
import axios from "axios";
import "./SignUp.css";

const SignUp = () => {
  const [formData, setFormData] = useState({
    fullName: "",
    email: "",
    password: "",
    role: "USER", // Default role
  });

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
      const response = await axios.post("http://localhost:8080/auth/signup", {
        username: formData.fullName,
        email: formData.email,
        password: formData.password,
        role: formData.role, // Send role with the other data
      });

      console.log("Signup successful:", response.data);
      alert("Account created successfully!"); // Or redirect user to login page
    } catch (err) {
      console.error("Error during signup:", err);
      setError("An error occurred. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="sign-up-container">
      <div className="form-box">
        <h2>Create an Account</h2>
        <p className="subtext">Start managing your tasks today!</p>
        {error && <p className="error-message">{error}</p>}
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            name="fullName"
            placeholder="Full Name"
            value={formData.fullName}
            onChange={handleChange}
            required
          />
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

          {/* Role Selection */}
          <div className="role-selection">
            <p>Select Role</p>
            <div className="role-options">
              <label className="role-option">
                <input
                  type="radio"
                  name="role"
                  value="USER"
                  checked={formData.role === "USER"}
                  onChange={handleChange}
                />
                User
              </label>
              <label className="role-option">
                <input
                  type="radio"
                  name="role"
                  value="ADMIN"
                  checked={formData.role === "ADMIN"}
                  onChange={handleChange}
                />
                Admin
              </label>
            </div>
          </div>

          <button type="submit" disabled={loading}>
            {loading ? "Creating Account..." : "Sign Up"}
          </button>
        </form>
        <p className="redirect">
          Already have an account? <a href="/signin">Sign In</a>
        </p>
      </div>
    </div>
  );
};

export default SignUp;
