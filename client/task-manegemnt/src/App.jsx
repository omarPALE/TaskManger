import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import LogIn from "../component/login/login";
import SignUp from "../component/signup/Signup";
import Home from "../component/home/Home";
import TaskPage from "../component/task/Task";
import { useState } from "react";

function App() {
  const [token, setToken] = useState("");

  return (
    <Router>
      {/* Navigation Bar */}
      <nav>
        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/task">Task Page</Link>
          </li>
          <li>
            <Link to="/signup">Sign Up</Link>
          </li>
          <li>
            <Link to="/signin">Sign In</Link>
          </li>
        </ul>
      </nav>

      {/* Routes */}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/task" element={<TaskPage token={token} />} />
        <Route path="/signup" element={<SignUp />} />
        <Route
          path="/signin"
          element={<LogIn token={token} setToken={setToken} />}
        />
      </Routes>
    </Router>
  );
}

export default App;
