
import { useState } from "react";
import "./SignUp.css";
import signupImage from "../assets/signup.avif";

function SignUp() {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [addressLine1, setAddressLine1] = useState("");
    const [addressLine2, setAddressLine2] = useState("");
    const [city, setCity] = useState("");
    const [state, setState] = useState("");
    const [postalCode, setPostalCode] = useState("");

    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    // Automatically format phone number as ###-###-####
    const handlePhoneChange = (value: string) => {
        const digits = value.replace(/\D/g, "").slice(0, 10);

        let formattedPhone = digits;

        if (digits.length > 6) {
            formattedPhone =
                digits.slice(0, 3) +
                "-" +
                digits.slice(3, 6) +
                "-" +
                digits.slice(6);
        } else if (digits.length > 3) {
            formattedPhone =
                digits.slice(0, 3) +
                "-" +
                digits.slice(3);
        }

        setPhoneNumber(formattedPhone);
    };

    // Allow only 5 numbers in the ZIP code
    const handleZipChange = (value: string) => {
        const digits = value.replace(/\D/g, "").slice(0, 5);
        setPostalCode(digits);
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setSuccessMessage("");
        setErrorMessage("");

        // Validate phone number
        if (!/^\d{3}-\d{3}-\d{4}$/.test(phoneNumber)) {
            setErrorMessage(
                "Phone number must be in the format ###-###-####."
            );
            return;
        }

        // Validate ZIP code
        if (!/^\d{5}$/.test(postalCode)) {
            setErrorMessage("ZIP code must contain exactly 5 digits.");
            return;
        }

        const authHeader = localStorage.getItem("authHeader") || "";

        const headers = {
            "Content-Type": "application/json",
            Authorization: authHeader,
        };

        const newLoanee = {
            firstName,
            lastName,
            phoneNumber,
            addressLine1,
            addressLine2,
            city,
            state,
            postalCode,
        };

        try {
            const response = await fetch("/api/loanees", {
                method: "POST",
                credentials: "include",
                headers,
                body: JSON.stringify(newLoanee),
            });

            if (response.ok) {
                setSuccessMessage("Loanee registered successfully!");

                // Reset form inputs
                setFirstName("");
                setLastName("");
                setPhoneNumber("");
                setAddressLine1("");
                setAddressLine2("");
                setCity("");
                setState("");
                setPostalCode("");
            } else {
                const text = await response.text();
                setErrorMessage(text || "Failed to register loanee.");
            }
        } catch (err) {
            console.error("Error saving loanee:", err);
            setErrorMessage("Network error while submitting form.");
        }
    };

    return (
        <div className="signup-page">
            <div className="signup-image">
                <img
                    src={signupImage}
                    alt="Technology loan program"
                />
            </div>

            <div className="signup-form-container">
                <h1>Sign-Up</h1>

                {successMessage && (
                    <p style={{ color: "green", marginBottom: "1rem" }}>
                        {successMessage}
                    </p>
                )}

                {errorMessage && (
                    <p style={{ color: "red", marginBottom: "1rem" }}>
                        {errorMessage}
                    </p>
                )}

                <form onSubmit={handleSubmit}>
                    <div className="form-section">
                        <label>Name</label>
                        <div className="name-fields">
                            <input
                                type="text"
                                placeholder="First"
                                value={firstName}
                                onChange={(e) => setFirstName(e.target.value)}
                                required
                            />

                            <input
                                type="text"
                                placeholder="Last"
                                value={lastName}
                                onChange={(e) => setLastName(e.target.value)}
                                required
                            />
                        </div>
                    </div>

                    <div className="form-section">
                        <label htmlFor="phone">Phone #</label>
                        <input
                            id="phone"
                            type="tel"
                            placeholder="###-###-####"
                            value={phoneNumber}
                            onChange={(e) => handlePhoneChange(e.target.value)}
                            maxLength={12}
                            pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"
                            title="Enter phone number as ###-###-####"
                            inputMode="numeric"
                            required
                        />
                    </div>

                    <div className="form-section">
                        <label htmlFor="address1">Address line 1</label>
                        <input
                            id="address1"
                            type="text"
                            value={addressLine1}
                            onChange={(e) => setAddressLine1(e.target.value)}
                            required
                        />
                    </div>

                    <div className="form-section">
                        <label htmlFor="address2">
                            Address line 2 (optional)
                        </label>
                        <input
                            id="address2"
                            type="text"
                            value={addressLine2}
                            onChange={(e) => setAddressLine2(e.target.value)}
                        />
                    </div>

                    <div className="location-fields">
                        <div>
                            <label htmlFor="city">City</label>
                            <input
                                id="city"
                                type="text"
                                value={city}
                                onChange={(e) => setCity(e.target.value)}
                                required
                            />
                        </div>

                        <div>
                            <label htmlFor="state">State</label>
                            <input
                                id="state"
                                type="text"
                                maxLength={2}
                                placeholder="FL"
                                value={state}
                                onChange={(e) =>
                                    setState(
                                        e.target.value
                                            .toUpperCase()
                                            .replace(/[^A-Z]/g, "")
                                    )
                                }
                                required
                            />
                        </div>

                        <div>
                            <label htmlFor="zip">Zip</label>
                            <input
                                id="zip"
                                type="text"
                                placeholder="#####"
                                value={postalCode}
                                onChange={(e) => handleZipChange(e.target.value)}
                                maxLength={5}
                                pattern="[0-9]{5}"
                                title="ZIP code must contain exactly 5 digits"
                                inputMode="numeric"
                                required
                            />
                        </div>
                    </div>

                    <button type="submit">Submit</button>
                </form>
            </div>
        </div>
    );
}

export default SignUp;
