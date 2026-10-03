import { useState } from "react";
import "./Login.css";
import loginImage from "../assets/login3.avif";

function Login() {
  // Stores the user email
  const [email, setEmail] = useState("");

  // Stores the user password
  const [password, setPassword] = useState("");

  // Runs when the user clicks Log In
  const handleLogin = (event: React.FormEvent) => {
    event.preventDefault();

    // Placeholder for backend
    console.log("Email:", email);
    console.log("Password:", password);
  };

  return (
    <div
      className="login-page"
      style={{ backgroundImage: `url(${loginImage})` }}
    >
      <div className="login-container">
        <h1>Orlando LendIT</h1>

        <p>Community Technology Loan Program</p>

        <form onSubmit={handleLogin}>
          <div className="form-group">
            <label htmlFor="email">Email</label>

            <input
              id="email"
              type="email"
              placeholder="Enter your email"
              value={email}
              onChange={(event) => setEmail(event.target.value)}
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