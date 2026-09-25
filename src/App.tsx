import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Participants from "./pages/Participants";
import Equipment from "./pages/Equipment";
import Loans from "./pages/Loans";
import Returns from "./pages/Returns";
import Analytics from "./pages/Analytics";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/login" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/participants" element={<Participants />} />
        <Route path="/equipment" element={<Equipment />} />
        <Route path="/loans" element={<Loans />} />
        <Route path="/returns" element={<Returns />} />
        <Route path="/analytics" element={<Analytics />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;