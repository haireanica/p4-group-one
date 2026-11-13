
import { useEffect, useMemo, useState } from "react";
import type { FormEvent } from "react";
import "./Loans.css";
import loansImage from "../assets/loans.jpg";

type Participant = {
    participantId: number;
    firstName: string;
    lastName: string;
    phoneNumber: string;
    addressLine1: string;
    addressLine2?: string;
    city: string;
    state: string;
    postalCode: string;
};

type Center = {
    centerId: number;
    name: string;
    address1?: string;
    city?: string;
    state?: string;
    postalCode?: string;
};

type Asset = {
    assetId: string;
    name: string;
    deviceType: string;
    status: string;
    inventory?: {
        inventoryId: number;
        center?: Center;
    };
};

function Loans() {
    const [participants, setParticipants] = useState<Participant[]>([]);
    const [assets, setAssets] = useState<Asset[]>([]);

    const [lastNameSearch, setLastNameSearch] = useState("");
    const [phoneSearch, setPhoneSearch] = useState("");

    const [selectedParticipant, setSelectedParticipant] =
        useState<Participant | null>(null);

    const [deviceType, setDeviceType] = useState("");
    const [centerId, setCenterId] = useState("");
    const [assetId, setAssetId] = useState("");

    const [loanPurpose, setLoanPurpose] = useState("");
    const [reason, setReason] = useState("");
    const [loanDays, setLoanDays] = useState("14");

    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const [loading, setLoading] = useState(true);

    // Load participants and equipment from the backend.
    useEffect(() => {
        const loadData = async () => {
            try {
                const [participantResponse, assetResponse] = await Promise.all([
                    fetch("/api/loanees", { credentials: "include" }),
                    fetch("/api/assets", { credentials: "include" }),
                ]);

                if (!participantResponse.ok) {
                    throw new Error("Unable to load participants.");
                }

                if (!assetResponse.ok) {
                    throw new Error("Unable to load equipment.");
                }

                const participantData: Participant[] =
                    await participantResponse.json();

                const assetData: Asset[] = await assetResponse.json();

                setParticipants(participantData);
                setAssets(assetData);
            } catch (err) {
                console.error("Error loading loan information:", err);
                setErrorMessage(
                    err instanceof Error
                        ? err.message
                        : "Unable to load loan information."
                );
            } finally {
                setLoading(false);
            }
        };

        loadData();
    }, []);

    // Search participants by last name and phone number.
    const filteredParticipants = useMemo(() => {
        const normalizedLastName = lastNameSearch.trim().toLowerCase();
        const normalizedPhone = phoneSearch.replace(/\D/g, "");

        // Do not show results until the user starts searching.
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

    // Only show equipment that is available.
    const availableAssets = assets.filter(
        (asset) => asset.status === "AVAILABLE"
    );

    // Get the available device types from the equipment records.
    const deviceTypes = Array.from(
        new Set(
            availableAssets
                .map((asset) => asset.deviceType)
                .filter((type) => Boolean(type))
        )
    ).sort();

    // Get the centers associated with available equipment.
    const centers = Array.from(
        new Map(
            availableAssets
                .filter((asset) => asset.inventory?.center)
                .map((asset) => [
                    asset.inventory!.center!.centerId,
                    asset.inventory!.center!,
                ])
        ).values()
    ).sort((a, b) => a.name.localeCompare(b.name));

    // Filter equipment using the selected device type and center.
    const matchingAssets = availableAssets.filter((asset) => {
        const matchesType = asset.deviceType === deviceType;

        const matchesCenter =
            String(asset.inventory?.center?.centerId ?? "") === centerId;

        return matchesType && matchesCenter;
    });

    const selectedAsset = matchingAssets.find(
        (asset) => asset.assetId === assetId
    );

    // Process the loan checkout.
    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        setSuccessMessage("");
        setErrorMessage("");

        if (!selectedParticipant || !selectedAsset) {
            setErrorMessage(
                "Please select a participant and an available device."
            );
            return;
        }

        const authHeader = localStorage.getItem("authHeader") || "";

        const checkoutPayload = {
            assetId: selectedAsset.assetId,
            participantId: selectedParticipant.participantId,
            reason: reason.trim(),
            loanPurpose: loanPurpose,
            days: Number(loanDays),
        };

        try {
            const response = await fetch("/api/loans/checkout", {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: authHeader,
                },
                body: JSON.stringify(checkoutPayload),
            });

            if (response.ok) {
                setSuccessMessage("Loan checked out successfully!");

                // Clear the selected device and loan details.
                setAssetId("");
                setLoanPurpose("");
                setReason("");
                setLoanDays("14");

                // Refresh equipment so the checked-out device is no longer available.
                const updatedAssets = await fetch("/api/assets", {
                    credentials: "include",
                });

                if (updatedAssets.ok) {
                    setAssets(await updatedAssets.json());
                }
            } else {
                const errorData = await response.json().catch(() => null);

                setErrorMessage(
                    errorData?.message || "Failed to process loan checkout."
                );
            }
        } catch (err) {
            console.error("Error checking out loan:", err);
            setErrorMessage(
                "Network error while processing loan checkout."
            );
        }
    };

    // Clear participant search.
    function clearSearch() {
        setLastNameSearch("");
        setPhoneSearch("");
        setSuccessMessage("");
        setErrorMessage("");
    }

    return (
        <div className="loans-page">
            <div className="loans-image">
                <img src={loansImage} alt="Technology loan program" />
            </div>

            <div className="loans-form-container">
                <h1>Loan Device</h1>

                {successMessage && (
                    <p className="loan-success" role="status">
                        {successMessage}
                    </p>
                )}

                {errorMessage && (
                    <p className="loan-error" role="alert">
                        {errorMessage}
                    </p>
                )}

                {loading ? (
                    <p>Loading participants and equipment...</p>
                ) : (
                    <>
                        {/* STEP 1: Select a participant */}
                        {!selectedParticipant ? (
                            <section className="loan-participant-search">
                                <h2>Participant Information</h2>

                                <div className="form-section">
                                    <label htmlFor="last-name-search">
                                        Last Name
                                    </label>

                                    <input
                                        id="last-name-search"
                                        type="text"
                                        placeholder="Search by last name"
                                        value={lastNameSearch}
                                        onChange={(e) =>
                                            setLastNameSearch(e.target.value)
                                        }
                                    />
                                </div>

                                <div className="form-section">
                                    <label htmlFor="phone-search">
                                        Phone Number
                                    </label>

                                    <input
                                        id="phone-search"
                                        type="tel"
                                        placeholder="Search by phone number"
                                        value={phoneSearch}
                                        onChange={(e) =>
                                            setPhoneSearch(e.target.value)
                                        }
                                    />
                                </div>

                                <div className="loan-search-actions">
                                    <button
                                        type="button"
                                        className="submit-button"
                                        onClick={clearSearch}
                                    >
                                        Clear
                                    </button>
                                </div>

                                <div className="loan-participant-results">
                                    {!lastNameSearch.trim() &&
                                    !phoneSearch.trim() ? (
                                        <p>
                                            Start typing a last name or phone
                                            number to find a participant.
                                        </p>
                                    ) : filteredParticipants.length === 0 ? (
                                        <p>
                                            No participants match your search.
                                        </p>
                                    ) : (
                                        filteredParticipants.map(
                                            (participant) => (
                                                <button
                                                    type="button"
                                                    className="loan-participant-option"
                                                    key={participant.participantId}
                                                    onClick={() => {
                                                        setSelectedParticipant(
                                                            participant
                                                        );
                                                        setSuccessMessage("");
                                                        setErrorMessage("");
                                                    }}
                                                >
                                                    <strong>
                                                        {participant.lastName},{" "}
                                                        {participant.firstName}
                                                    </strong>

                                                    <span>
                                                        {participant.phoneNumber}
                                                    </span>
                                                </button>
                                            )
                                        )
                                    )}
                                </div>
                            </section>
                        ) : (
                            <div className="loan-selected-participant">
                                <h2>Selected Participant</h2>

                                <h3>
                                    {selectedParticipant.firstName}{" "}
                                    {selectedParticipant.lastName}
                                </h3>

                                <p>
                                    Participant ID:{" "}
                                    {selectedParticipant.participantId}
                                </p>

                                <p>
                                    Phone: {selectedParticipant.phoneNumber}
                                </p>

                                <p>
                                    Address: {selectedParticipant.addressLine1},{" "}
                                    {selectedParticipant.city},{" "}
                                    {selectedParticipant.state}{" "}
                                    {selectedParticipant.postalCode}
                                </p>

                                <button
                                    type="button"
                                    className="loan-change-participant"
                                    onClick={() => {
                                        setSelectedParticipant(null);
                                        setAssetId("");
                                        setDeviceType("");
                                        setCenterId("");
                                        setLoanPurpose("");
                                        setReason("");
                                        setLoanDays("14");
                                    }}
                                >
                                    Change Participant
                                </button>
                            </div>
                        )}

                        {/* STEP 2: Select equipment */}
                        {selectedParticipant && (
                            <>
                                <h2>Equipment Selection</h2>

                                <div className="form-section">
                                    <label htmlFor="device-type">
                                        Device Type
                                    </label>

                                    <select
                                        id="device-type"
                                        value={deviceType}
                                        onChange={(e) => {
                                            setDeviceType(e.target.value);
                                            setCenterId("");
                                            setAssetId("");
                                        }}
                                        required
                                    >
                                        <option value="">
                                            Select device type
                                        </option>

                                        {deviceTypes.map((type) => (
                                            <option key={type} value={type}>
                                                {type}
                                            </option>
                                        ))}
                                    </select>
                                </div>

                                {deviceType && (
                                    <div className="form-section">
                                        <label htmlFor="center-location">
                                            Center Location
                                        </label>

                                        <select
                                            id="center-location"
                                            value={centerId}
                                            onChange={(e) => {
                                                setCenterId(e.target.value);
                                                setAssetId("");
                                            }}
                                            required
                                        >
                                            <option value="">
                                                Select center
                                            </option>

                                            {centers
                                                .filter((center) =>
                                                    availableAssets.some(
                                                        (asset) =>
                                                            asset.deviceType ===
                                                            deviceType &&
                                                            asset.inventory
                                                                ?.center
                                                                ?.centerId ===
                                                            center.centerId
                                                    )
                                                )
                                                .map((center) => (
                                                    <option
                                                        key={center.centerId}
                                                        value={center.centerId}
                                                    >
                                                        {center.name}
                                                    </option>
                                                ))}
                                        </select>
                                    </div>
                                )}

                                {deviceType && centerId && (
                                    <div className="form-section">
                                        <label>
                                            Select Available Device
                                        </label>

                                        {matchingAssets.length > 0 ? (
                                            <div className="loan-device-results">
                                                {matchingAssets.map((asset) => (
                                                    <label
                                                        className="loan-device-option"
                                                        key={asset.assetId}
                                                    >
                                                        <input
                                                            type="radio"
                                                            name="selected-device"
                                                            value={asset.assetId}
                                                            checked={
                                                                assetId ===
                                                                asset.assetId
                                                            }
                                                            onChange={() =>
                                                                setAssetId(
                                                                    asset.assetId
                                                                )
                                                            }
                                                        />

                                                        <span>
                                                            <strong>
                                                                {asset.name}
                                                            </strong>

                                                            <small>
                                                                {asset.deviceType}
                                                            </small>

                                                            <small>
                                                                {asset.assetId}
                                                            </small>
                                                        </span>
                                                    </label>
                                                ))}
                                            </div>
                                        ) : (
                                            <p>
                                                No available devices at this
                                                center.
                                            </p>
                                        )}
                                    </div>
                                )}

                                {/* STEP 3: Enter loan details */}
                                {selectedAsset && (
                                    <form onSubmit={handleSubmit}>
                                        <h2>Loan Details</h2>

                                        <p className="loan-selected-device">
                                            Selected Device:{" "}
                                            <strong>
                                                {selectedAsset.name}
                                            </strong>
                                        </p>

                                        <div className="form-section">
                                            <label htmlFor="loan-purpose">
                                                Loan Purpose
                                            </label>

                                            <select
                                                id="loan-purpose"
                                                value={loanPurpose}
                                                onChange={(e) =>
                                                    setLoanPurpose(
                                                        e.target.value
                                                    )
                                                }
                                                required
                                            >
                                                <option value="">
                                                    Select purpose
                                                </option>

                                                <option value="EDUCATION">
                                                    Education
                                                </option>

                                                <option value="EMPLOYMENT">
                                                    Employment
                                                </option>

                                                <option value="PERSONAL">
                                                    Personal
                                                </option>

                                                <option value="OTHER">
                                                    Other
                                                </option>
                                            </select>
                                        </div>

                                        <div className="form-section">
                                            <label htmlFor="reason">
                                                Reason for Loan
                                            </label>

                                            <textarea
                                                id="reason"
                                                placeholder="Enter reason for loan"
                                                value={reason}
                                                onChange={(e) =>
                                                    setReason(e.target.value)
                                                }
                                                required
                                            />
                                        </div>

                                        <div className="form-section">
                                            <label htmlFor="loan-length">
                                                Length of Loan
                                            </label>

                                            <select
                                                id="loan-length"
                                                value={loanDays}
                                                onChange={(e) =>
                                                    setLoanDays(e.target.value)
                                                }
                                                required
                                            >
                                                <option value="7">
                                                    1 Week
                                                </option>

                                                <option value="14">
                                                    2 Weeks
                                                </option>

                                                <option value="21">
                                                    3 Weeks
                                                </option>

                                                <option value="28">
                                                    4 Weeks
                                                </option>
                                            </select>
                                        </div>

                                        <button type="submit">
                                            Submit Loan
                                        </button>
                                    </form>
                                )}
                            </>
                        )}
                    </>
                )}
            </div>
        </div>
    );
}

export default Loans;
