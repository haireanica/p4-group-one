import "./Dashboard.css";
import loginImage from "../assets/login3.avif";
function Dashboard() {
  return (
    <div className="dashboard-page"
    style={{ backgroundImage: `url(${loginImage})` }}
    >
   


      {/* Dashboard header */}
      <div className="dashboard-header">
        <h1>Welcome to Orlando Lend IT!</h1>
        <p>Current Data:</p>
      </div>

      {/* Summary cards */}
      <div className="dashboard-cards">

        {/* Participants card */}
        <div className="dashboard-card">
          <h2>Participants</h2>
          <p>0</p>
          <span>Total participants</span>
        </div>

        {/* Equipment card */}
        <div className="dashboard-card">
          <h2>Equipment</h2>
          <p>0</p>
          <span>Available devices</span>
        </div>

        {/* Active loans card */}
        <div className="dashboard-card">
          <h2>Active Loans</h2>
          <p>0</p>
          <span>Currently checked out</span>
        </div>

        {/* Overdue card */}
        <div className="dashboard-card">
          <h2>Overdue</h2>
          <p>0</p>
          <span>Past due returns</span>
        </div>

      </div>

      {/* Recent activity */}
      <div className="recent-activity">

        <h2>Recent Activity</h2>

        <p>No recent activity.</p>

      </div>

    </div>
  );
}

export default Dashboard;