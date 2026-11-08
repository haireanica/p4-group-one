import { useState } from "react";
import "./Loans.css";
import loansImage from "../assets/loans.jpg";

function Loans() {
  const [participantId, setParticipantId] = useState("");
  const [assetId, setAssetId] = useState("");
  const [loanPurpose, setLoanPurpose] = useState("");
  const [reason, setReason] = useState("");
  const [loanDays, setLoanDays] = useState("14");

  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSuccessMessage("");
    setErrorMessage("");

    const authHeader = localStorage.getItem("authHeader") || "";
    const headers = {
      "Content-Type": "application/json",
      Authorization: authHeader,
    };

    const checkoutPayload = {
      assetId: assetId.trim(),
      participantId: participantId ? Number(participantId) : null,
      reason: reason.trim(),
      loanPurpose: loanPurpose ? loanPurpose : null,
      days: Number(loanDays),
    };

    try {
      const response = await fetch("/api/loans/checkout", {
        method: "POST",
        headers,
        body: JSON.stringify(checkoutPayload),
      });

      if (response.ok) {
        setSuccessMessage("Loan checked out successfully!");
        setParticipantId("");
        setAssetId("");
        setLoanPurpose("");
        setReason("");
        setLoanDays("14");
      } else {
        const errorData = await response.json().catch(() => null);
        const message = errorData?.message || "Failed to process loan checkout.";
        setErrorMessage(message);
      }
    } catch (err) {
      console.error("Error checking out loan:", err);
      setErrorMessage("Network error while processing loan checkout.");
    }
  };

  return (
    <div className="loans-page">
      <div className="loans-image">
        <img src={loansImage} alt="Technology loan program" />
      </div>

      <div className="loans-form-container">
        <h1>Request a Loan</h1>

        {successMessage && <p style={{ color: "green", marginBottom: "1rem" }}>{successMessage}</p>}
        {errorMessage && <p style={{ color: "red", marginBottom: "1rem" }}>{errorMessage}</p>}

        <form onSubmit={handleSubmit}>
          <div className="form-section">
            <label htmlFor="participant-id">Participant ID #</label>
            <input
              id="participant-id"
              type="number"
              placeholder="Enter participant ID"
              value={participantId}
              onChange={(e) => setParticipantId(e.target.value)}
              required
            />
          </div>

          <div className="form-section">
            <label htmlFor="device-id">Device ID #</label>
            <input
              id="device-id"
              type="text"
              placeholder="Enter device ID"
              value={assetId}
              onChange={(e) => setAssetId(e.target.value)}
              required
            />
          </div>

          <div className="form-section">
            <label htmlFor="loan-purpose">Loan Purpose</label>
            <select
              id="loan-purpose"
              value={loanPurpose}
              onChange={(e) => setLoanPurpose(e.target.value)}
              required
            >
              <option value="">Select purpose</option>
              <option value="EDUCATION">Education</option>
              <option value="EMPLOYMENT">Employment</option>
              <option value="PERSONAL">Personal</option>
              <option value="OTHER">Other</option>
            </select>
          </div>

          <div className="form-section">
            <label htmlFor="reason">Reason for Loan</label>
            <textarea
              id="reason"
              placeholder="Enter reason for loan"
              value={reason}
              onChange={(e) => setReason(e.target.value)}
              required
            ></textarea>
          </div>

          <div className="form-section">
            <label htmlFor="loan-length">Length of Loan</label>
            <select
              id="loan-length"
              value={loanDays}
              onChange={(e) => setLoanDays(e.target.value)}
              required
            >
              <option value="7">1 Week</option>
              <option value="14">2 Weeks</option>
              <option value="21">3 Weeks</option>
              <option value="28">4 Weeks</option>
            </select>
          </div>

          <button type="submit">Submit</button>
        </form>
      </div>
    </div>
  );
}

export default Loans;