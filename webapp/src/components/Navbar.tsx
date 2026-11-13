import { Link } from "react-router-dom";
import "./Navbar.css";


function Navbar() {
    return (
        <nav>
            <Link to="/dashboard">Dashboard</Link>{" "}
            <Link to="/signup">Sign Up</Link>{" "}
            <Link to="/participants">Participants</Link>{" "}
            <Link to="/loans">Loans</Link>{" "}
            <Link to="/returns">Returns</Link>{" "}
            <Link to="/equipment">Inventory</Link>{" "}
            <Link to="/analytics">Analytics</Link>
        </nav>
    );
}

export default Navbar;