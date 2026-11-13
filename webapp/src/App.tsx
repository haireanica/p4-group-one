
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";

import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Signup from "./pages/SignUp";
import Equipment from "./pages/Equipment";
import Loans from "./pages/Loans";
import Returns from "./pages/Returns";
import Analytics from "./pages/Analytics";
import Participants from "./pages/Participants"

function App() {
    return (
        <BrowserRouter>
            <Routes>
                {/* Login pages without the Navbar */}
                <Route path="/" element={<Login />} />
                <Route path="/login" element={<Login />} />

                {/* Pages with the left-side Navbar */}
                <Route
                    path="/dashboard"
                    element={
                        <div className="app-layout">
                            <Navbar />
                            <main className="main-content">
                                <Dashboard />
                            </main>
                        </div>
                    }
                />

                <Route
                    path="/signup"
                    element={
                        <div className="app-layout">
                            <Navbar />
                            <main className="main-content">
                                <Signup />
                            </main>
                        </div>
                    }
                />

                <Route
                    path="/participants"
                    element={
                        <div className="app-layout">
                            <Navbar />
                            <main className="main-content">
                                <Participants />
                            </main>
                        </div>
                    }
                />

                <Route
                    path="/equipment"
                    element={
                        <div className="app-layout">
                            <Navbar />
                            <main className="main-content">
                                <Equipment />
                            </main>
                        </div>
                    }
                />

                <Route
                    path="/loans"
                    element={
                        <div className="app-layout">
                            <Navbar />
                            <main className="main-content">
                                <Loans />
                            </main>
                        </div>
                    }
                />

                <Route
                    path="/returns"
                    element={
                        <div className="app-layout">
                            <Navbar />
                            <main className="main-content">
                                <Returns />
                            </main>
                        </div>
                    }
                />

                <Route
                    path="/analytics"
                    element={
                        <div className="app-layout">
                            <Navbar />
                            <main className="main-content">
                                <Analytics />
                            </main>
                        </div>
                    }
                />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
