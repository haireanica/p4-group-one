
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

        fetch("/api/analytics/loan-return-trends", { headers })
            .then((res) => (res.ok ? res.json() : []))
            .then((data) => setTrends(data))
            .catch((err) => console.error("Error fetching loan return trends:", err));

        fetch("/api/analytics/device-status", { headers })
            .then((res) => (res.ok ? res.json() : []))
            .then((data) => setDeviceStatuses(data))
            .catch((err) => console.error("Error fetching device status:", err));

        fetch("/api/analytics/loan-status-distribution", { headers })
            .then((res) => (res.ok ? res.json() : []))
            .then((data) => setLoanStatuses(data))
            .catch((err) => console.error("Error fetching loan status distribution:", err));

        fetch("/api/analytics/recent-activity", { headers })
            .then((res) => (res.ok ? res.json() : []))
            .then((data) => setRecentActivity(data))
            .catch((err) => console.error("Error fetching recent activity:", err));
    }, []);

    // Calculate Y-axis values
    const maxTrendValue = Math.max(
        ...trends.map((trend) => trend.totalLoanActivity),
        1
    );

    const axisMaxValue = Math.ceil(maxTrendValue / 5) * 5;

    const yAxisLabels = Array.from(
        { length: 6 },
        (_, index) => axisMaxValue - (index * axisMaxValue) / 5
    );

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

            {/* Loan Activity Chart */}
            <div className="analytics-section">
                <h2>Loan Activity</h2>

                {trends.length === 0 ? (
                    <p>No activity trend data available.</p>
                ) : (
                    <div className="activity-chart-layout">

                        {/* Y-Axis */}
                        <div className="activity-y-axis">
                            {yAxisLabels.map((value, index) => (
                                <span key={index}>{value}</span>
                            ))}
                        </div>

                        {/* Chart */}
                        <div className="activity-chart-content">
                            <div className="activity-chart-plot">

                                {/* Horizontal Gridlines */}
                                <div className="chart-gridlines">
                                    {yAxisLabels.map((value, index) => (
                                        <div className="chart-gridline" key={index}></div>
                                    ))}
                                </div>

                                {/* Bars */}
                                {trends.map((trend, index) => {
                                    const heightPercent =
                                        (trend.totalLoanActivity / axisMaxValue) * 100;

                                    return (
                                        <div className="chart-bar" key={index}>
                                            <div
                                                className="bar"
                                                style={{
                                                    height: `${heightPercent}%`,
                                                }}
                                                title={`${trend.totalLoanActivity} loans`}
                                            ></div>
                                        </div>
                                    );
                                })}
                            </div>

                            {/* X-Axis */}
                            <div className="activity-x-axis">
                                {trends.map((trend, index) => (
                                    <span key={index}>{trend.dayOfWeek}</span>
                                ))}
                            </div>
                        </div>
                    </div>
                )}
            </div>

            {/* Equipment and Loan Statistics */}
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
