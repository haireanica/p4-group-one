import { useEffect, useState } from "react";
import "./Dashboard.css";
import loginImage from "../assets/login3.avif";

interface RecentActivity {
  title: string;
  description: string;
  timeAgo: string;
}

function Dashboard() {
  const [username, setUsername] = useState<string>("User");
  const [totalParticipants, setTotalParticipants] = useState<number>(0);
  const [totalEquipment, setTotalEquipment] = useState<number>(0);
  const [activeLoans, setActiveLoans] = useState<number>(0);
  const [overdueLoans, setOverdueLoans] = useState<number>(0);
  const [recentActivity, setRecentActivity] = useState<RecentActivity[]>([]);

  useEffect(() => {
    const savedUser = localStorage.getItem("user");
    if (savedUser) {
      try {
        const userData = JSON.parse(savedUser);
        if (userData.username) {
          setUsername(userData.username);
        }
      } catch (e) {
        console.error("Failed to parse user data", e);
      }
    }

    const authHeader = localStorage.getItem("authHeader") || "";
    const headers = {
      "Content-Type": "application/json",
      "Authorization": authHeader,
    };

    // Fetch Total Participants
    fetch("/api/analytics/total-participants", { headers })
      .then((res) => (res.ok ? res.json() : 0))
      .then((data) => setTotalParticipants(data))
      .catch((err) => console.error("Error fetching participants:", err));

    // Fetch Total Equipment
    fetch("/api/analytics/total-equipment", { headers })
      .then((res) => (res.ok ? res.json() : 0))
      .then((data) => setTotalEquipment(data))
      .catch((err) => console.error("Error fetching equipment:", err));

    // Fetch Active Loans count
    fetch("/api/analytics/active-loans", { headers })
      .then((res) => (res.ok ? res.json() : 0))
      .then((data) => setActiveLoans(data))
      .catch((err) => console.error("Error fetching active loans:", err));

    // Fetch Overdue Loans count
    fetch("/api/analytics/overdue-loans-count", { headers })
      .then((res) => (res.ok ? res.json() : 0))
      .then((data) => setOverdueLoans(data))
      .catch((err) => console.error("Error fetching overdue loans:", err));

    // Fetch Recent Activity
    fetch("/api/analytics/recent-activity", { headers })
      .then((res) => (res.ok ? res.json() : []))
      .then((data) => setRecentActivity(data))
      .catch((err) => console.error("Error fetching recent activity:", err));
  }, []);

  return (
    <div
      className="dashboard-page"
      style={{ backgroundImage: `url(${loginImage})` }}
    >
      {/* Dashboard header */}
      <div className="dashboard-header">
        <h1>Welcome back, {username}!</h1>
        <p>Orlando Lend IT - Current Data:</p>
      </div>

      {/* Summary cards */}
      <div className="dashboard-cards">
        {/* Participants card */}
        <div className="dashboard-card">
          <h2>Participants</h2>
          <p>{totalParticipants}</p>
          <span>Total participants</span>
        </div>

        {/* Equipment card */}
        <div className="dashboard-card">
          <h2>Equipment</h2>
          <p>{totalEquipment}</p>
          <span>Available devices</span>
        </div>

        {/* Active loans card */}
        <div className="dashboard-card">
          <h2>Active Loans</h2>
          <p>{activeLoans}</p>
          <span>Currently checked out</span>
        </div>

        {/* Overdue card */}
        <div className="dashboard-card">
          <h2>Overdue</h2>
          <p>{overdueLoans}</p>
          <span>Past due returns</span>
        </div>
      </div>

      {/* Recent activity */}
      <div className="recent-activity">
        <h2>Recent Activity</h2>
        {recentActivity.length === 0 ? (
          <p>No recent activity.</p>
        ) : (
          <ul>
            {recentActivity.map((activity, index) => (
              <li key={index} style={{ marginBottom: "0.5rem" }}>
                <strong>{activity.title}</strong>: {activity.description} <em>({activity.timeAgo})</em>
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
}

export default Dashboard;