
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";

import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Signup from "./pages/SignUp";
import Equipment from "./pages/Equipment";
import Loans from "./pages/Loans";
import Returns from "./pages/Returns";
import Analytics from "./pages/Analytics";

function App() {
  return (
    <BrowserRouter>
      <Navbar />

      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/login" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/equipment" element={<Equipment />} />
        <Route path="/loans" element={<Loans />} />
        <Route path="/returns" element={<Returns />} />
        <Route path="/analytics" element={<Analytics />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
