import { useState, useEffect } from "react";
import "./Returns.css";
import returnImage from "../assets/return.jpg";

interface LoanItem {
  loanId: number;
  assetId: string;
  assetName: string;
  participantId: number;
  reason: string;
  checkoutDate: string;
  dueDate: string;
  returnDate: string | null;
  status: string;
}

function Returns() {
  const [searchTerm, setSearchTerm] = useState("");
  const [loans, setLoans] = useState<LoanItem[]>([]);
  const [selectedLoan, setSelectedLoan] = useState<LoanItem | null>(null);

  const [returnDate, setReturnDate] = useState("");
  const [condition, setCondition] = useState("");
  const [notes, setNotes] = useState("");

  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    fetchLoans();
  }, []);

  const fetchLoans = async () => {
    const authHeader = localStorage.getItem("authHeader") || "";
    try {
      const response = await fetch("/api/loans", {
        headers: { Authorization: authHeader },
      });
      if (response.ok) {
        const data = await response.json();
        setLoans(data);
      }
    } catch (err) {
      console.error("Error fetching loans:", err);
    }
  };

  const handleSearch = () => {
    setErrorMessage("");
    setSuccessMessage("");

    const term = searchTerm.trim().toLowerCase();
    if (!term) {
      setErrorMessage("Please enter a search term (Device ID).");
      return;
    }

    const found = loans.find(
      (loan) =>
        loan.assetId?.toLowerCase().includes(term) &&
        loan.status === "CHECKED_OUT"
    );

    if (found) {
      setSelectedLoan(found);
      setErrorMessage("");
    } else {
      setSelectedLoan(null);
      setErrorMessage("No active loan found for this Device ID.");
    }
  };

  const handleSubmitReturn = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!selectedLoan) return;

    setSuccessMessage("");
    setErrorMessage("");

    const authHeader = localStorage.getItem("authHeader") || "";
    const headers = {
      "Content-Type": "application/json",
      Authorization: authHeader,
    };

    const returnPayload = {
      condition: condition || "good",
      notes: notes.trim(),
    };

    try {
      const response = await fetch(`/api/loans/${selectedLoan.loanId}/return`, {
        method: "POST",
        headers,
        body: JSON.stringify(returnPayload),
      });

      if (response.ok) {
        const msg = condition && condition !== "good" 
          ? `Device ${selectedLoan.assetId} returned and flagged for maintenance!` 
          : `Device ${selectedLoan.assetId} successfully returned!`;
        
        setSuccessMessage(msg);
        setSelectedLoan(null);
        setSearchTerm("");
        setReturnDate("");
        setCondition("");
        setNotes("");
        fetchLoans();
      } else {
        const text = await response.text();
        setErrorMessage(text || "Failed to process device return.");
      }
    } catch (err) {
      console.error("Error returning device:", err);
      setErrorMessage("Network error while processing return.");
    }
  };

  return (
    <div className="returns-page">
      <div className="returns-image">
        <img src={returnImage} alt="Technology loan program" />
      </div>

      <div className="returns-container">
        <h1>Return Device</h1>

        {successMessage && <p style={{ color: "green", marginBottom: "1rem" }}>{successMessage}</p>}
        {errorMessage && <p style={{ color: "red", marginBottom: "1rem" }}>{errorMessage}</p>}

        <div className="loan-search">
          <label htmlFor="loan-search">Search Loan</label>

          <div className="search-bar">
            <input
              id="loan-search"
              type="text"
              placeholder="Search by Device ID #"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />

            <button type="button" onClick={handleSearch}>
              Search
            </button>
          </div>
        </div>

        <div className="return-card">
          <div className="loan-details">
            <h2>Loan Details</h2>

            <div className="detail">
              <span>Participant ID</span>
              <p>{selectedLoan ? selectedLoan.participantId : "N/A"}</p>
            </div>

            <div className="detail">
              <span>Device Name</span>
              <p>{selectedLoan ? selectedLoan.assetName : "N/A"}</p>
            </div>

            <div className="detail">
              <span>Device ID #</span>
              <p>{selectedLoan ? selectedLoan.assetId : "N/A"}</p>
            </div>

            <div className="detail">
              <span>Loan Start Date</span>
              <p>{selectedLoan ? selectedLoan.checkoutDate : "N/A"}</p>
            </div>

            <div className="detail">
              <span>Loan Due Date</span>
              <p>{selectedLoan ? selectedLoan.dueDate : "N/A"}</p>
            </div>
          </div>

          <div className="return-form">
            <h2>Return Condition</h2>

            <form onSubmit={handleSubmitReturn}>
              <div className="form-section">
                <label htmlFor="return-date">Return Date</label>
                <input
                  id="return-date"
                  type="date"
                  value={returnDate}
                  onChange={(e) => setReturnDate(e.target.value)}
                  required
                />
              </div>

              <div className="form-section">
                <label>Condition of Device</label>

                <div className="condition-options">
                  <label>
                    <input
                      type="radio"
                      name="condition"
                      value="good"
                      checked={condition === "good"}
                      onChange={(e) => setCondition(e.target.value)}
                      required
                    />
                    Good
                  </label>

                  <label>
                    <input
                      type="radio"
                      name="condition"
                      value="minor-damage"
                      checked={condition === "minor-damage"}
                      onChange={(e) => setCondition(e.target.value)}
                    />
                    Minor Damage
                  </label>

                  <label>
                    <input
                      type="radio"
                      name="condition"
                      value="major-damage"
                      checked={condition === "major-damage"}
                      onChange={(e) => setCondition(e.target.value)}
                    />
                    Major Damage
                  </label>

                  <label>
                    <input
                      type="radio"
                      name="condition"
                      value="needs-repair"
                      checked={condition === "needs-repair"}
                      onChange={(e) => setCondition(e.target.value)}
                    />
                    Needs Repair
                  </label>
                </div>
              </div>

              <div className="form-section">
                <label htmlFor="notes">Notes</label>
                <textarea
                  id="notes"
                  placeholder="Enter notes about the returned device"
                  value={notes}
                  onChange={(e) => setNotes(e.target.value)}
                ></textarea>
              </div>

              <button
                className="submit-button"
                type="submit"
                disabled={!selectedLoan}
              >
                Submit
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Returns;