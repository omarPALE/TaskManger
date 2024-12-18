import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import LogIn from "../component/login/login";
import SignUp from "../component/signup/Signup";
import Home from "../component/home/Home";
import TaskPage from "../component/task/Task";
import { useState } from "react";
import Styles from "./Styles";
import Report from "../component/report/Report";
function App() {
  const [token, setToken] = useState("");
  const [reload, setReload] = useState(false);
  console.log("reload state", reload);
  return (
    <Router>
      {/* Navigation Bar */}
      <nav className="navbar" style={{ width: "inhert" }}>
        <ul className="navbar-links" style={Styles.navbar}>
          <li>
            <Link to="/" style={Styles.navbarLink}>
              Home
            </Link>
          </li>
          <li>
            <Link
              to="/task"
              style={Styles.navbarLink}
              reload={reload}
              setReload={setReload}
              onClick={() => {
                setReload(!reload);
              }}
            >
              Task Page
            </Link>
          </li>
          <li>
            <Link to="/signup" style={Styles.navbarLink}>
              Sign Up
            </Link>
          </li>
          <li>
            <Link to="/signin" style={Styles.navbarLink}>
              Sign In
            </Link>
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
        <Route path="/report" element={<Report />} />
      </Routes>
    </Router>
  );
}

export default App;
