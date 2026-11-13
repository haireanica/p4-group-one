
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Dashboard.css";
import loginImage from "../assets/login3.avif";

interface RecentActivity {
    title: string;
    description: string;
    timeAgo: string;
}

function Dashboard() {
    const navigate = useNavigate();

    const [username, setUsername] = useState<string>("User");
    const [totalParticipants, setTotalParticipants] = useState<number>(0);
    const [totalEquipment, setTotalEquipment] = useState<number>(0);
    const [activeLoans, setActiveLoans] = useState<number>(0);
    const [overdueLoans, setOverdueLoans] = useState<number>(0);
    const [recentActivity, setRecentActivity] = useState<RecentActivity[]>([]);
    const [loggingOut, setLoggingOut] = useState<boolean>(false);
    const [logoutError, setLogoutError] = useState<string>("");

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

        const headers = {
            "Content-Type": "application/json",
        };

        const fetchOptions = {
            headers,
            credentials: "include" as RequestCredentials,
        };

        // Fetch Total Participants
        fetch("/api/analytics/total-participants", fetchOptions)
            .then((res) => (res.ok ? res.json() : 0))
            .then((data) => setTotalParticipants(data))
            .catch((err) => console.error("Error fetching participants:", err));

        // Fetch Total Equipment
        fetch("/api/analytics/total-equipment", fetchOptions)
            .then((res) => (res.ok ? res.json() : 0))
            .then((data) => setTotalEquipment(data))
            .catch((err) => console.error("Error fetching equipment:", err));

        // Fetch Active Loans count
        fetch("/api/analytics/active-loans", fetchOptions)
            .then((res) => (res.ok ? res.json() : 0))
            .then((data) => setActiveLoans(data))
            .catch((err) => console.error("Error fetching active loans:", err));

        // Fetch Overdue Loans count
        fetch("/api/analytics/overdue-loans-count", fetchOptions)
            .then((res) => (res.ok ? res.json() : 0))
            .then((data) => setOverdueLoans(data))
            .catch((err) => console.error("Error fetching overdue loans:", err));

        // Fetch Recent Activity
        fetch("/api/analytics/recent-activity", fetchOptions)
            .then((res) => (res.ok ? res.json() : []))
            .then((data) => setRecentActivity(data))
            .catch((err) => console.error("Error fetching recent activity:", err));
    }, []);

    // Log out the current user
    const handleLogout = async () => {
        setLoggingOut(true);
        setLogoutError("");

        try {
            const response = await fetch("/api/users/logout", {
                method: "POST",
                credentials: "include",
            });

            if (!response.ok) {
                throw new Error("Logout request failed.");
            }

            // Clear saved login information
            localStorage.removeItem("user");
            localStorage.removeItem("authHeader");

            // Return to the login screen
            navigate("/login", { replace: true });

        } catch (error) {
            console.error("Logout error:", error);
            setLogoutError("Unable to log out. Please try again.");
            setLoggingOut(false);
        }
    };

    return (
        <div
            className="dashboard-page"
            style={{ backgroundImage: `url(${loginImage})` }}
        >
            {/* Dashboard header */}
            <div className="dashboard-header">
                <div>
                    <h1>Welcome back, {username}!</h1>
                    <p>Orlando Lend IT - Current Data:</p>
                </div>

                <button
                    className="logout-button"
                    onClick={handleLogout}
                    disabled={loggingOut}
                >
                    {loggingOut ? "Logging Out..." : "Log Out"}
                </button>
            </div>

            {logoutError && (
                <p className="logout-error">{logoutError}</p>
            )}

            {/* Summary cards */}
            <div className="dashboard-cards">
                <div className="dashboard-card">
                    <h2>Participants</h2>
                    <p>{totalParticipants}</p>
                    <span>Total participants</span>
                </div>

                <div className="dashboard-card">
                    <h2>Equipment</h2>
                    <p>{totalEquipment}</p>
                    <span>Available devices</span>
                </div>

                <div className="dashboard-card">
                    <h2>Active Loans</h2>
                    <p>{activeLoans}</p>
                    <span>Currently checked out</span>
                </div>

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
                                <strong>{activity.title}</strong>: {activity.description}{" "}
                                <em>({activity.timeAgo})</em>
                            </li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    );
}

export default Dashboard;
