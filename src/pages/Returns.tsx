import "./Returns.css";
import returnImage from "../assets/return.jpg";

function Returns() {
  return (
    <div className="returns-page">
      <div className="returns-image">
        <img src={returnImage} alt="Technology loan program" />
      </div>

      <div className="returns-container">
        <h1>Return Device</h1>

        <div className="loan-search">
          <label htmlFor="loan-search">Search Loan</label>

          <div className="search-bar">
            <input
              id="loan-search"
              type="text"
              placeholder="Search by Device ID # or Participant Name"
            />

            <button type="button">Search</button>
          </div>
        </div>

        <div className="return-card">
          <div className="loan-details">
            <h2>Loan Details</h2>

            <div className="detail">
              <span>Participant Name</span>
              <p>John Doe</p>
            </div>

            <div className="detail">
              <span>Device Type</span>
              <p>Laptop</p>
            </div>

            <div className="detail">
              <span>Device ID #</span>
              <p>LT-00123</p>
            </div>

            <div className="detail">
              <span>Loan Start Date</span>
              <p>10/01/2026</p>
            </div>

            <div className="detail">
              <span>Loan Return Date</span>
              <p>10/15/2026</p>
            </div>
          </div>

          <div className="return-form">
            <h2>Return Condition</h2>

            <form>
              <div className="form-section">
                <label htmlFor="return-date">Return Date</label>
                <input
                  id="return-date"
                  type="date"
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
                    />
                    Good
                  </label>

                  <label>
                    <input
                      type="radio"
                      name="condition"
                      value="minor-damage"
                    />
                    Minor Damage
                  </label>

                  <label>
                    <input
                      type="radio"
                      name="condition"
                      value="major-damage"
                    />
                    Major Damage
                  </label>

                  <label>
                    <input
                      type="radio"
                      name="condition"
                      value="needs-repair"
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
                ></textarea>
              </div>

              <button className="submit-button" type="submit">
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