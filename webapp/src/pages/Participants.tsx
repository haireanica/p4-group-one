
import { useEffect, useMemo, useState } from "react";
import "./Participants.css";

type Participant = {
    participantId: number;
    firstName: string;
    lastName: string;
    phoneNumber: string;
    addressLine1: string;
    addressLine2: string;
    city: string;
    state: string;
    postalCode: string;
};

type Loan = {
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
};

function Participants() {
    const [participants, setParticipants] = useState<Participant[]>([]);
    const [loans, setLoans] = useState<Loan[]>([]);

    const [lastNameSearch, setLastNameSearch] = useState("");
    const [phoneSearch, setPhoneSearch] = useState("");
    const [selectedId, setSelectedId] = useState<number | null>(null);

    const [showLoans, setShowLoans] = useState(false);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    // Load participants and loans from the Spring Boot API
    useEffect(() => {
        async function loadData() {
            try {
                setLoading(true);
                setError("");

                const [participantResponse, loanResponse] = await Promise.all([
                    fetch("/api/loanees", {
                        credentials: "include",
                    }),
                    fetch("/api/loans", {
                        credentials: "include",
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

                const loanData: Loan[] = await loanResponse.json();

                setParticipants(participantData);
                setLoans(loanData);

                if (participantData.length > 0) {
                    setSelectedId(participantData[0].participantId);
                }
            } catch (err) {
                setError(
                    err instanceof Error
                        ? err.message
                        : "Something went wrong while loading the data."
                );
            } finally {
                setLoading(false);
            }
        }

        loadData();
    }, []);

    // Find the currently selected participant
    const selectedParticipant = participants.find(
        (participant) => participant.participantId === selectedId
    );

    // Find loans belonging to the selected participant
    const participantLoans = loans.filter(
        (loan) => loan.participantId === selectedId
    );

    // Search participants by last name and phone number
    const filteredParticipants = useMemo(() => {
        const normalizedLastName = lastNameSearch.trim().toLowerCase();
        const normalizedPhone = phoneSearch.replace(/\D/g, "");

        return participants.filter((participant) => {
            const matchesLastName = participant.lastName
                .toLowerCase()
                .includes(normalizedLastName);

            const participantPhone = participant.phoneNumber.replace(/\D/g, "");

            const matchesPhone = participantPhone.includes(normalizedPhone);

            return matchesLastName && matchesPhone;
        });
    }, [participants, lastNameSearch, phoneSearch]);

    // Clear the search fields
    function clearSearch() {
        setLastNameSearch("");
        setPhoneSearch("");
    }

    // Select a participant from the directory
    function selectParticipant(id: number) {
        setSelectedId(id);
        setShowLoans(false);
    }

    // Display the participant's complete address
    function getAddress(participant: Participant) {
        return [
            participant.addressLine1,
            participant.addressLine2,
            participant.city,
            participant.state,
            participant.postalCode,
        ]
            .filter(Boolean)
            .join(", ");
    }

    // Display dates in a readable format
    function formatDate(date: string | null) {
        if (!date) return "Not available";

        return new Date(`${date}T00:00:00`).toLocaleDateString();
    }

    if (loading) {
        return (
            <div className="participants-page">
                <p>Loading participants...</p>
            </div>
        );
    }

    return (
        <div className="participants-page">
            <header className="participants-header">
                <div>
                    <h1>Participants</h1>
                    <p>Search and manage community program participants.</p>
                </div>
            </header>

            {error && (
                <p className="participant-error" role="alert">
                    {error}
                </p>
            )}

            <section className="participant-search">
                <div className="participant-search-field">
                    <label htmlFor="last-name-search">Last Name</label>
                    <input
                        id="last-name-search"
                        type="text"
                        placeholder="Search by last name"
                        value={lastNameSearch}
                        onChange={(event) =>
                            setLastNameSearch(event.target.value)
                        }
                    />
                </div>

                <div className="participant-search-field">
                    <label htmlFor="phone-search">Phone Number</label>
                    <input
                        id="phone-search"
                        type="tel"
                        placeholder="Search by phone number"
                        value={phoneSearch}
                        onChange={(event) =>
                            setPhoneSearch(event.target.value)
                        }
                    />
                </div>

                <button
                    type="button"
                    className="participant-secondary-button clear-search-button"
                    onClick={clearSearch}
                >
                    Clear
                </button>
            </section>

            <div className="participants-workspace">
                {/* Participant directory */}
                <section className="participant-list-panel">
                    <div className="participant-panel-heading">
                        <h2>Participant Directory</h2>
                        <span>{filteredParticipants.length} found</span>
                    </div>

                    {filteredParticipants.length === 0 ? (
                        <p className="participant-empty-state">
                            No participants match your search.
                        </p>
                    ) : (
                        <div className="participant-table-wrapper">
                            <table className="participant-table">
                                <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Phone</th>
                                </tr>
                                </thead>

                                <tbody>
                                {filteredParticipants.map((participant) => (
                                    <tr
                                        key={participant.participantId}
                                        className={
                                            selectedId === participant.participantId
                                                ? "selected-participant"
                                                : ""
                                        }
                                        onClick={() =>
                                            selectParticipant(
                                                participant.participantId
                                            )
                                        }
                                    >
                                        <td>
                                            <button
                                                type="button"
                                                className="participant-name-button"
                                                onClick={() =>
                                                    selectParticipant(
                                                        participant.participantId
                                                    )
                                                }
                                            >
                                                {participant.lastName},{" "}
                                                {participant.firstName}
                                            </button>
                                        </td>

                                        <td>{participant.phoneNumber}</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    )}
                </section>

                {/* Participant details */}
                <section className="participant-details-panel">
                    {showLoans && selectedParticipant ? (
                        <>
                            <div className="participant-panel-heading">
                                <div>
                                    <h2>Loan History</h2>
                                    <p>
                                        {selectedParticipant.firstName}{" "}
                                        {selectedParticipant.lastName}
                                    </p>
                                </div>

                                <button
                                    type="button"
                                    className="participant-secondary-button"
                                    onClick={() => setShowLoans(false)}
                                >
                                    Back to Details
                                </button>
                            </div>

                            {participantLoans.length === 0 ? (
                                <p className="participant-empty-state">
                                    This participant has no loan records.
                                </p>
                            ) : (
                                <div className="participant-loan-list">
                                    {participantLoans.map((loan) => (
                                        <article
                                            className="participant-loan-card"
                                            key={loan.loanId}
                                        >
                                            <div>
                                                <h3>
                                                    {loan.assetName || "Unknown Equipment"}
                                                </h3>

                                                <p>
                                                    Checked out:{" "}
                                                    {formatDate(loan.checkoutDate)}
                                                </p>

                                                <p>
                                                    Due date:{" "}
                                                    {formatDate(loan.dueDate)}
                                                </p>

                                                <p>
                                                    Returned:{" "}
                                                    {formatDate(loan.returnDate)}
                                                </p>

                                                {loan.loanPurpose && (
                                                    <p>
                                                        Purpose: {loan.loanPurpose}
                                                    </p>
                                                )}
                                            </div>

                                            <span
                                                className={`loan-status loan-status-${(
                                                    loan.status || "unknown"
                                                ).toLowerCase()}`}
                                            >
                                                {loan.status || "Unknown"}
                                            </span>
                                        </article>
                                    ))}
                                </div>
                            )}
                        </>
                    ) : selectedParticipant ? (
                        <>
                            <div className="participant-panel-heading">
                                <h2>Participant Details</h2>
                                <span className="participant-id">
                                    ID: {selectedParticipant.participantId}
                                </span>
                            </div>

                            <div className="participant-profile">
                                <div
                                    className="participant-avatar"
                                    aria-hidden="true"
                                >
                                    {selectedParticipant.firstName.charAt(0)}
                                    {selectedParticipant.lastName.charAt(0)}
                                </div>

                                <div>
                                    <h3>
                                        {selectedParticipant.firstName}{" "}
                                        {selectedParticipant.lastName}
                                    </h3>
                                    <p>Program Participant</p>
                                </div>
                            </div>

                            <dl className="participant-info-list">
                                <div>
                                    <dt>Phone Number</dt>
                                    <dd>{selectedParticipant.phoneNumber}</dd>
                                </div>

                                <div>
                                    <dt>Address</dt>
                                    <dd>
                                        {getAddress(selectedParticipant) ||
                                            "Not provided"}
                                    </dd>
                                </div>

                                <div>
                                    <dt>Total Loan Records</dt>
                                    <dd>{participantLoans.length}</dd>
                                </div>

                                <div>
                                    <dt>Active Loans</dt>
                                    <dd>
                                        {participantLoans.filter((loan) =>
                                            ["ACTIVE", "OVERDUE"].includes(
                                                (loan.status || "").toUpperCase()
                                            )
                                        ).length}
                                    </dd>
                                </div>
                            </dl>

                            <div className="participant-detail-actions">
                                <button
                                    type="button"
                                    className="participant-primary-button"
                                    onClick={() => setShowLoans(true)}
                                >
                                    View Loans
                                </button>
                            </div>
                        </>
                    ) : (
                        <div className="participant-empty-detail">
                            <h2>No Participant Selected</h2>
                            <p>
                                Select someone from the directory to view their
                                information.
                            </p>
                        </div>
                    )}
                </section>
            </div>
        </div>
    );
}

export default Participants;
