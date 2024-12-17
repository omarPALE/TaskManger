import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LogIn from "../component/login/login";
import SignUp from "../component/signup/Signup";
import Home from "../component/home/Home";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />

        <Route path="/signup" element={<SignUp />} />
        <Route path="/signin" element={<LogIn />} />
      </Routes>
    </Router>
  );
}

export default App;
