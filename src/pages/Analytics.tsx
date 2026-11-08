import { useEffect, useState } from "react";
import "./Analytics.css";
import loginImage from "../assets/login3.avif";

interface LoanReturnTrend {
  dayOfWeek: string;
  totalLoanActivity: number;
}

interface DeviceStatusDistribution {
  status: string;
  count: number;
}

interface LoanStatusDistribution {
  status: string;
  count: number;
}

interface RecentActivity {
  title: string;
  description: string;
  timeAgo: string;
}

function Analytics() {
  const [totalLoans, setTotalLoans] = useState<number>(0);
  const [activeLoans, setActiveLoans] = useState<number>(0);
  const [returnedLoans, setReturnedLoans] = useState<number>(0);
  const [overdueLoans, setOverdueLoans] = useState<number>(0);

  const [trends, setTrends] = useState<LoanReturnTrend[]>([]);
  const [deviceStatuses, setDeviceStatuses] = useState<DeviceStatusDistribution[]>([]);
  const [loanStatuses, setLoanStatuses] = useState<LoanStatusDistribution[]>([]);
  const [recentActivity, setRecentActivity] = useState<RecentActivity[]>([]);

  useEffect(() => {
    const authHeader = localStorage.getItem("authHeader") || "";
    const headers = {
      "Content-Type": "application/json",
      Authorization: authHeader,
    };

    // Summary Cards
    fetch("/api/analytics/total-loans", { headers })
      .then((res) => (res.ok ? res.json() : 0))
      .then((data) => setTotalLoans(data))
      .catch((err) => console.error("Error fetching total loans:", err));

    fetch("/api/analytics/active-loans", { headers })
      .then((res) => (res.ok ? res.json() : 0))
      .then((data) => setActiveLoans(data))
      .catch((err) => console.error("Error fetching active loans:", err));

    fetch("/api/analytics/returned-loans", { headers })
      .then((res) => (res.ok ? res.json() : 0))
      .then((data) => setReturnedLoans(data))
      .catch((err) => console.error("Error fetching returned loans:", err));

    fetch("/api/analytics/overdue-loans-count", { headers })
      .then((res) => (res.ok ? res.json() : 0))
      .then((data) => setOverdueLoans(data))
      .catch((err) => console.error("Error fetching overdue loans:", err));

    // Weekly Trends Bar Chart Data
    fetch("/api/analytics/loan-return-trends", { headers })
      .then((res) => (res.ok ? res.json() : []))
      .then((data) => setTrends(data))
      .catch((err) => console.error("Error fetching loan return trends:", err));

    // Device Status Distribution
    fetch("/api/analytics/device-status", { headers })
      .then((res) => (res.ok ? res.json() : []))
      .then((data) => setDeviceStatuses(data))
      .catch((err) => console.error("Error fetching device status:", err));

    // Loan Status Distribution
    fetch("/api/analytics/loan-status-distribution", { headers })
      .then((res) => (res.ok ? res.json() : []))
      .then((data) => setLoanStatuses(data))
      .catch((err) => console.error("Error fetching loan status distribution:", err));

    // Recent Activity
    fetch("/api/analytics/recent-activity", { headers })
      .then((res) => (res.ok ? res.json() : []))
      .then((data) => setRecentActivity(data))
      .catch((err) => console.error("Error fetching recent activity:", err));
  }, []);

  // Helper to calculate relative bar chart heights dynamically
  const maxTrendValue = Math.max(...trends.map((t) => t.totalLoanActivity), 1);

  return (
    <div
      className="analytics-page"
      style={{ backgroundImage: `url(${loginImage})` }}
    >
      {/* Page Header */}
      <div className="analytics-header">
        <h1>Analytics</h1>
        <p>View lending activity and equipment statistics.</p>
      </div>

      {/* Summary Cards */}
      <div className="analytics-cards">
        <div className="analytics-card">
          <h3>Total Loans</h3>
          <p className="analytics-number">{totalLoans}</p>
        </div>

        <div className="analytics-card">
          <h3>Active Loans</h3>
          <p className="analytics-number">{activeLoans}</p>
        </div>

        <div className="analytics-card">
          <h3>Returned</h3>
          <p className="analytics-number">{returnedLoans}</p>
        </div>

        <div className="analytics-card">
          <h3>Overdue</h3>
          <p className="analytics-number">{overdueLoans}</p>
        </div>
      </div>

      {/* Loan Activity Bar Chart */}
      <div className="analytics-section">
        <h2>Loan Activity</h2>
        <div className="activity-chart">
          {trends.length === 0 ? (
            <p style={{ textAlign: "center", width: "100%" }}>No activity trend data available.</p>
          ) : (
            trends.map((trend, index) => {
              const heightPercent = Math.round((trend.totalLoanActivity / maxTrendValue) * 100);
              return (
                <div className="chart-bar" key={index}>
                  <div className="bar" style={{ height: `${Math.max(heightPercent, 10)}%` }}></div>
                  <span>{trend.dayOfWeek}</span>
                </div>
              );
            })
          )}
        </div>
      </div>

      {/* Equipment and Loan Statistics Grid */}
      <div className="analytics-grid">
        <div className="analytics-section">
          <h2>Equipment Status</h2>
          {deviceStatuses.length === 0 ? (
            <p>No equipment status data.</p>
          ) : (
            deviceStatuses.map((ds, index) => (
              <div className="stat-row" key={index}>
                <span>{ds.status}</span>
                <strong>{ds.count}</strong>
              </div>
            ))
          )}
        </div>

        <div className="analytics-section">
          <h2>Loan Status</h2>
          {loanStatuses.length === 0 ? (
            <p>No loan status data.</p>
          ) : (
            loanStatuses.map((ls, index) => (
              <div className="stat-row" key={index}>
                <span>{ls.status}</span>
                <strong>{ls.count}</strong>
              </div>
            ))
          )}
        </div>
      </div>

      {/* Recent Activity */}
      <div className="analytics-section">
        <h2>Recent Activity</h2>
        <div className="recent-activity">
          {recentActivity.length === 0 ? (
            <p>No recent activity recorded.</p>
          ) : (
            recentActivity.map((activity, index) => (
              <div className="activity-row" key={index}>
                <div>
                  <strong>{activity.title}</strong>
                  <p>{activity.description}</p>
                </div>
                <span>{activity.timeAgo}</span>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
}

export default Analytics;