
import { useEffect, useMemo, useState, type FormEvent } from "react";
import "./Returns.css";
import returnImage from "../assets/return.jpg";

interface Participant {
    participantId: number;
    firstName: string;
    lastName: string;
    phoneNumber: string;
}

interface LoanItem {
    loanId: number;
    assetId: string | null;
    assetName: string | null;
    center: string | null;
    participantId: number | null;
    reason: string | null;
    loanPurpose: string | null;
    checkoutDate: string | null;
    dueDate: string | null;
    returnDate: string | null;
    status: string | null;
}

function Returns() {
    const [participants, setParticipants] = useState<Participant[]>([]);
    const [loans, setLoans] = useState<LoanItem[]>([]);

    const [lastNameSearch, setLastNameSearch] = useState("");
    const [phoneSearch, setPhoneSearch] = useState("");

    const [selectedParticipant, setSelectedParticipant] =
        useState<Participant | null>(null);

    const [selectedLoan, setSelectedLoan] = useState<LoanItem | null>(null);

    const [returnDate, setReturnDate] = useState("");
    const [condition, setCondition] = useState("");
    const [notes, setNotes] = useState("");

    const [loading, setLoading] = useState(true);
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    // Load participants and loans
    useEffect(() => {
        async function loadData() {
            try {
                setLoading(true);
                setErrorMessage("");

                const [participantResponse, loanResponse] = await Promise.all([
                    fetch("/api/loanees", {
                        credentials: "include",
                    }),
                    fetch("/api/loans", {
                        credentials: "include",
                        headers: {
                            Authorization: localStorage.getItem("authHeader") || "",
                        },
                    }),
                ]);

                if (!participantResponse.ok) {
                    throw new Error("Could not load participants.");
                }

                if (!loanResponse.ok) {
                    throw new Error("Could not load loan records.");
                }

                const participantData: Participant[] =
                    await participantResponse.json();

                const loanData: LoanItem[] = await loanResponse.json();

                setParticipants(participantData);
                setLoans(loanData);
            } catch (err) {
                setErrorMessage(
                    err instanceof Error
                        ? err.message
                        : "Something went wrong while loading data."
                );
            } finally {
                setLoading(false);
            }
        }

        loadData();
    }, []);

    // Search participants by last name and phone number
    const filteredParticipants = useMemo(() => {
        const normalizedLastName = lastNameSearch.trim().toLowerCase();
        const normalizedPhone = phoneSearch.replace(/\D/g, "");

        // Do not show results until the user starts searching
        if (!normalizedLastName && !normalizedPhone) {
            return [];
        }

        return participants.filter((participant) => {
            const matchesLastName = (participant.lastName || "")
                .toLowerCase()
                .includes(normalizedLastName);

            const participantPhone = (participant.phoneNumber || "")
                .replace(/\D/g, "");

            const matchesPhone = participantPhone.includes(normalizedPhone);

            return matchesLastName && matchesPhone;
        });
    }, [participants, lastNameSearch, phoneSearch]);

    // Find checked-out devices belonging to the selected participant
    const participantLoans = useMemo(() => {
        if (!selectedParticipant) {
            return [];
        }

        return loans.filter(
            (loan) =>
                loan.participantId === selectedParticipant.participantId &&
                (loan.status || "").toUpperCase() === "CHECKED_OUT" &&
                !loan.returnDate
        );
    }, [loans, selectedParticipant]);

    // Select a participant
    function selectParticipant(participant: Participant) {
        setSelectedParticipant(participant);
        setSelectedLoan(null);
        setSuccessMessage("");
        setErrorMessage("");
        setCondition("");
        setNotes("");
        setReturnDate("");
    }

    // Clear participant search
    function clearSearch() {
        setLastNameSearch("");
        setPhoneSearch("");
        setSelectedParticipant(null);
        setSelectedLoan(null);
        setSuccessMessage("");
        setErrorMessage("");
        setCondition("");
        setNotes("");
        setReturnDate("");
    }

    // Refresh loans after a return
    async function refreshLoans() {
        const response = await fetch("/api/loans", {
            credentials: "include",
            headers: {
                Authorization: localStorage.getItem("authHeader") || "",
            },
        });

        if (!response.ok) {
            throw new Error("Return was processed, but loan records could not be refreshed.");
        }

        const data: LoanItem[] = await response.json();
        setLoans(data);
    }

    // Format dates
    function formatDate(date: string | null) {
        if (!date) return "N/A";

        return new Date(`${date}T00:00:00`).toLocaleDateString();
    }

    // Process the selected device return
    async function handleSubmitReturn(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();

        if (!selectedLoan) {
            setErrorMessage("Please select a device to return.");
            return;
        }

        if (!condition) {
            setErrorMessage("Please select the device condition.");
            return;
        }

        setSuccessMessage("");
        setErrorMessage("");

        const authHeader = localStorage.getItem("authHeader") || "";

        const returnPayload = {
            condition: condition,
            notes: notes.trim(),
        };

        try {
            const response = await fetch(
                `/api/loans/${selectedLoan.loanId}/return`,
                {
                    method: "POST",
                    credentials: "include",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: authHeader,
                    },
                    body: JSON.stringify(returnPayload),
                }
            );

            if (response.ok) {
                const message =
                    condition !== "good"
                        ? `${selectedLoan.assetName} was returned and flagged for maintenance!`
                        : `${selectedLoan.assetName} was successfully returned!`;

                setSuccessMessage(message);

                setSelectedLoan(null);
                setReturnDate("");
                setCondition("");
                setNotes("");

                await refreshLoans();
            } else {
                const text = await response.text();
                setErrorMessage(text || "Failed to process device return.");
            }
        } catch (err) {
            console.error("Error returning device:", err);
            setErrorMessage(
                err instanceof Error
                    ? err.message
                    : "Network error while processing return."
            );
        }
    }

    if (loading) {
        return (
            <div className="returns-page">
                <p>Loading return information...</p>
            </div>
        );
    }

    return (
        <div className="returns-page">
            <div className="returns-image">
                <img src={returnImage} alt="Technology loan program" />
            </div>

            <div className="returns-container">
                <h1>Return Device</h1>

                {successMessage && (
                    <p className="return-success" role="status">
                        {successMessage}
                    </p>
                )}

                {errorMessage && (
                    <p className="return-error" role="alert">
                        {errorMessage}
                    </p>
                )}

                {/* Participant Search */}
                {!selectedParticipant ? (
                    <section className="return-participant-search">
                        <h2>Participant Information</h2>

                        <div className="form-section">
                            <label htmlFor="return-last-name">
                                Last Name
                            </label>
                            <input
                                id="return-last-name"
                                type="text"
                                placeholder="Search by last name"
                                value={lastNameSearch}
                                onChange={(e) =>
                                    setLastNameSearch(e.target.value)
                                }
                            />
                        </div>

                        <div className="form-section">
                            <label htmlFor="return-phone">
                                Phone Number
                            </label>
                            <input
                                id="return-phone"
                                type="tel"
                                placeholder="Search by phone number"
                                value={phoneSearch}
                                onChange={(e) =>
                                    setPhoneSearch(e.target.value)
                                }
                            />
                        </div>

                        <div className="return-search-actions">
                            <button
                                type="button"
                                className="submit-button"
                                onClick={clearSearch}
                            >
                                Clear
                            </button>
                        </div>

                        <div className="return-participant-results">
                            {!lastNameSearch.trim() && !phoneSearch.trim() ? (
                                <p>
                                    Start typing a last name or phone number
                                    to find a participant.
                                </p>
                            ) : filteredParticipants.length === 0 ? (
                                <p>No participants match your search.</p>
                            ) : (
                                filteredParticipants.map((participant) => (
                                    <button
                                        type="button"
                                        className="return-participant-option"
                                        key={participant.participantId}
                                        onClick={() =>
                                            selectParticipant(participant)
                                        }
                                    >
                                        <strong>
                                            {participant.lastName},{" "}
                                            {participant.firstName}
                                        </strong>
                                        <span>{participant.phoneNumber}</span>
                                    </button>
                                ))
                            )}
                        </div>
                    </section>
                ) : (
                    <>
                        {/* Selected Participant */}
                        <section className="return-selected-participant">
                            <h2>Selected Participant</h2>

                            <p>
                                <strong>
                                    {selectedParticipant.firstName}{" "}
                                    {selectedParticipant.lastName}
                                </strong>
                            </p>

                            <p>
                                Phone: {selectedParticipant.phoneNumber}
                            </p>

                            <button
                                type="button"
                                className="change-participant-button"
                                onClick={() => {
                                    setSelectedParticipant(null);
                                    setSelectedLoan(null);
                                    setSuccessMessage("");
                                    setErrorMessage("");
                                }}
                            >
                                Change Participant
                            </button>
                        </section>

                        {/* Checked-Out Devices */}
                        <section className="return-device-section">
                            <h2>Checked-Out Devices</h2>

                            {participantLoans.length === 0 ? (
                                <p>
                                    This participant has no devices currently
                                    checked out.
                                </p>
                            ) : (
                                <div className="return-device-results">
                                    {participantLoans.map((loan) => (
                                        <label
                                            className="return-device-option"
                                            key={loan.loanId}
                                        >
                                            <input
                                                type="radio"
                                                name="selected-return-device"
                                                checked={
                                                    selectedLoan?.loanId ===
                                                    loan.loanId
                                                }
                                                onChange={() => {
                                                    setSelectedLoan(loan);
                                                    setErrorMessage("");
                                                }}
                                            />

                                            <span>
                                                <strong>
                                                    {loan.assetName ||
                                                        "Unknown Device"}
                                                </strong>
                                                <small>
                                                    Checked out:{" "}
                                                    {formatDate(
                                                        loan.checkoutDate
                                                    )}
                                                </small>
                                                <small>
                                                    Due:{" "}
                                                    {formatDate(loan.dueDate)}
                                                </small>
                                            </span>
                                        </label>
                                    ))}
                                </div>
                            )}
                        </section>

                        {/* Selected Device and Return Form */}
                        {selectedLoan && (
                            <div className="return-card">
                                <div className="loan-details">
                                    <h2>Loan Details</h2>

                                    <div className="detail">
                                        <span>Participant</span>
                                        <p>
                                            {selectedParticipant.firstName}{" "}
                                            {selectedParticipant.lastName}
                                        </p>
                                    </div>

                                    <div className="detail">
                                        <span>Device Name</span>
                                        <p>
                                            {selectedLoan.assetName || "N/A"}
                                        </p>
                                    </div>

                                    <div className="detail">
                                        <span>Device ID</span>
                                        <p>{selectedLoan.assetId || "N/A"}</p>
                                    </div>

                                    <div className="detail">
                                        <span>Loan Start Date</span>
                                        <p>
                                            {formatDate(
                                                selectedLoan.checkoutDate
                                            )}
                                        </p>
                                    </div>

                                    <div className="detail">
                                        <span>Loan Due Date</span>
                                        <p>
                                            {formatDate(selectedLoan.dueDate)}
                                        </p>
                                    </div>
                                </div>

                                <div className="return-form">
                                    <h2>Return Condition</h2>

                                    <form onSubmit={handleSubmitReturn}>
                                        <div className="form-section">
                                            <label htmlFor="return-date">
                                                Return Date
                                            </label>
                                            <input
                                                id="return-date"
                                                type="date"
                                                value={returnDate}
                                                onChange={(e) =>
                                                    setReturnDate(
                                                        e.target.value
                                                    )
                                                }
                                                required
                                            />
                                        </div>

                                        <div className="form-section">
                                            <label>
                                                Condition of Device
                                            </label>

                                            <div className="condition-options">
                                                <label>
                                                    <input
                                                        type="radio"
                                                        name="condition"
                                                        value="good"
                                                        checked={
                                                            condition === "good"
                                                        }
                                                        onChange={(e) =>
                                                            setCondition(
                                                                e.target.value
                                                            )
                                                        }
                                                        required
                                                    />
                                                    Good
                                                </label>

                                                <label>
                                                    <input
                                                        type="radio"
                                                        name="condition"
                                                        value="minor-damage"
                                                        checked={
                                                            condition ===
                                                            "minor-damage"
                                                        }
                                                        onChange={(e) =>
                                                            setCondition(
                                                                e.target.value
                                                            )
                                                        }
                                                    />
                                                    Minor Damage
                                                </label>

                                                <label>
                                                    <input
                                                        type="radio"
                                                        name="condition"
                                                        value="major-damage"
                                                        checked={
                                                            condition ===
                                                            "major-damage"
                                                        }
                                                        onChange={(e) =>
                                                            setCondition(
                                                                e.target.value
                                                            )
                                                        }
                                                    />
                                                    Major Damage
                                                </label>

                                                <label>
                                                    <input
                                                        type="radio"
                                                        name="condition"
                                                        value="needs-repair"
                                                        checked={
                                                            condition ===
                                                            "needs-repair"
                                                        }
                                                        onChange={(e) =>
                                                            setCondition(
                                                                e.target.value
                                                            )
                                                        }
                                                    />
                                                    Needs Repair
                                                </label>
                                            </div>
                                        </div>

                                        <div className="form-section">
                                            <label htmlFor="return-notes">
                                                Notes
                                            </label>
                                            <textarea
                                                id="return-notes"
                                                placeholder="Enter notes about the returned device"
                                                value={notes}
                                                onChange={(e) =>
                                                    setNotes(e.target.value)
                                                }
                                            />
                                        </div>

                                        <button
                                            className="submit-button"
                                            type="submit"
                                        >
                                            Submit Return
                                        </button>
                                    </form>
                                </div>
                            </div>
                        )}
                    </>
                )}
            </div>
        </div>
    );
}

export default Returns;
