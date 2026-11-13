import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Login.css";
import loginImage from "../assets/login3.avif";

function Login() {
    // Stores the username
    const [username, setUsername] = useState("");

    // Stores the user password
    const [password, setPassword] = useState("");

    // Stores any login error message
    const [error, setError] = useState("");

    const navigate = useNavigate();

    // Runs when the user clicks Log In
    const handleLogin = async (event: React.FormEvent) => {
        event.preventDefault();
        setError("");

        try {
            const response = await fetch('/api/users/login', {
                method: 'POST',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
            });

            if (response.ok) {
                navigate('/dashboard');
            } else {
                setError('Invalid username or password');
            }
        } catch (err) {
            setError('An error occurred connecting to the server.');
        }
    };

    return (
        <div
            className="login-page"
            style={{ backgroundImage: `url(${loginImage})` }}
        >
            <div className="login-container">
                <h1>Orlando LendIT</h1>

                <p>Community Technology Loan Program</p>

                {error && <p className="error-message" style={{ color: "red", marginBottom: "1rem" }}>{error}</p>}

                <form onSubmit={handleLogin}>
                    <div className="form-group">
                        <label htmlFor="username">Username</label>

                        <input
                            id="username"
                            type="text"
                            placeholder="Enter your username"
                            value={username}
                            onChange={(event) => setUsername(event.target.value)}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="password">Password</label>

                        <input
                            id="password"
                            type="password"
                            placeholder="Enter your password"
                            value={password}
                            onChange={(event) => setPassword(event.target.value)}
                            required
                        />
                    </div>

                    <button type="submit">Log In</button>
                </form>
            </div>
        </div>
    );
}

export default Login;