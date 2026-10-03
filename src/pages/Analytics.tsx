
import "./Analytics.css";
import loginImage from "../assets/login3.avif";

function Analytics() {
  return (
    <div className="analytics-page" 
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
          <p className="analytics-number">124</p>
        </div>

        <div className="analytics-card">
          <h3>Active Loans</h3>
          <p className="analytics-number">38</p>
        </div>

        <div className="analytics-card">
          <h3>Returned</h3>
          <p className="analytics-number">79</p>
        </div>

        <div className="analytics-card">
          <h3>Overdue</h3>
          <p className="analytics-number">7</p>
        </div>

      </div>

      {/* Loan Activity */}
      <div className="analytics-section">
        <h2>Loan Activity</h2>

        <div className="activity-chart">

          <div className="chart-bar">
            <div className="bar" style={{ height: "55%" }}></div>
            <span>Mon</span>
          </div>

          <div className="chart-bar">
            <div className="bar" style={{ height: "75%" }}></div>
            <span>Tue</span>
          </div>

          <div className="chart-bar">
            <div className="bar" style={{ height: "45%" }}></div>
            <span>Wed</span>
          </div>

          <div className="chart-bar">
            <div className="bar" style={{ height: "85%" }}></div>
            <span>Thu</span>
          </div>

          <div className="chart-bar">
            <div className="bar" style={{ height: "65%" }}></div>
            <span>Fri</span>
          </div>

          <div className="chart-bar">
            <div className="bar" style={{ height: "35%" }}></div>
            <span>Sat</span>
          </div>

          <div className="chart-bar">
            <div className="bar" style={{ height: "25%" }}></div>
            <span>Sun</span>
          </div>

        </div>
      </div>

      {/* Equipment and Loan Statistics */}
      <div className="analytics-grid">

        <div className="analytics-section">
          <h2>Equipment Status</h2>

          <div className="stat-row">
            <span>Available</span>
            <strong>42</strong>
          </div>

          <div className="stat-row">
            <span>On Loan</span>
            <strong>38</strong>
          </div>

          <div className="stat-row">
            <span>Maintenance</span>
            <strong>6</strong>
          </div>

        </div>

        <div className="analytics-section">
          <h2>Loan Status</h2>

          <div className="stat-row">
            <span>Active</span>
            <strong>38</strong>
          </div>

          <div className="stat-row">
            <span>Returned</span>
            <strong>79</strong>
          </div>

          <div className="stat-row">
            <span>Overdue</span>
            <strong>7</strong>
          </div>

        </div>

      </div>

      {/* Recent Activity */}
      <div className="analytics-section">
        <h2>Recent Activity</h2>

        <div className="recent-activity">

          <div className="activity-row">
            <div>
              <strong>Laptop #102 returned</strong>
              <p>Returned by John Smith</p>
            </div>
            <span>Today</span>
          </div>

          <div className="activity-row">
            <div>
              <strong>Tablet #204 loaned</strong>
              <p>Loaned to Jane Doe</p>
            </div>
            <span>Yesterday</span>
          </div>

          <div className="activity-row">
            <div>
              <strong>Hotspot #055 available</strong>
              <p>Equipment returned and marked available</p>
            </div>
            <span>2 days ago</span>
          </div>

        </div>
      </div>

    </div>
  );
}

export default Analytics;

