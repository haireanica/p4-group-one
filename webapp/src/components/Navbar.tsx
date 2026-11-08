import { Link } from "react-router-dom";

function Navbar() {
  return (
    <nav>
      <Link to="/dashboard">Dashboard</Link>{" "}
      <Link to="/signup">SignUp</Link>{" "}
      <Link to="/equipment">Equipment</Link>{" "}
      <Link to="/loans">Loans</Link>{" "}
      <Link to="/returns">Returns</Link>{" "}
      <Link to="/analytics">Analytics</Link>
    </nav>
  );
}

export default Navbar;