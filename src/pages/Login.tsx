import { useState } from "react";
import "./Login.css";


function Login() {
//stores user email entered
const [email, setEmail] = useState("");

//stores user password entered
const [password, setPassword] = useState("");

//runs when user clicks 'Login'
const handleLogin = (event: React.FormEvent) => {
  event.preventDefault();


//placeholder for backend, displays entered info to console
console.log("Email: " , email);
console.log("Password: " , password);
};
    return (
      <div className="login-page">
        {/*Main login container*/}
        <div className="login-container">
         {/*Project name*/} 
         <h1>Orlando Lend IT</h1>
         {/*Description*/}
         <p>Community Technology Loan Program</p>
         {/*Login form*/}
         <form onSubmit={handleLogin}>


          {/*Email Field*/}
          <div className= "form-group">
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

          {/*Password Field*/}
          <div className= "form-group">
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
            
            {/*Login button*/}
            <button type="submit">
              Log In
            </button>
 
          </form> 
      </div>

    </div>
    );
  }
  
  export default Login;