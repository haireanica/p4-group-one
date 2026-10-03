import "./Loans.css";
import loansImage from "../assets/loans.jpg";



function Loans() {
  return (
    <div className="loans-page">
      <div className="loans-image">
        <img src={loansImage} alt="Technology loan program" />
      </div>

      <div className="loans-form-container">
        <h1>Request a Loan</h1>

        <form>
          <div className="form-section">
            <label htmlFor="device-type">Device Type</label>
            <select id="device-type">
              <option value="">Select a device</option>
              <option value="laptop">Laptop</option>
              <option value="tablet">Tablet</option>
              <option value="phone">Phone</option>
            </select>
          </div>

          <div className="form-section">
            <label htmlFor="device-id">Device ID #</label>
            <input
              id="device-id"
              type="text"
              placeholder="Enter device ID"
            />
          </div>

          <div className="form-section">
            <label htmlFor="reason">Reason for Loan</label>
            <textarea
              id="reason"
              placeholder="Enter reason for loan"
            ></textarea>
          </div>

          <div className="form-section">
            <label htmlFor="loan-length">Length of Loan</label>
            <select id="loan-length">
              <option value="">Select loan length</option>
              <option value="1-week">1 Week</option>
              <option value="2-weeks">2 Weeks</option>
              <option value="3-weeks">3 Weeks</option>
              <option value="4-weeks">4 Weeks</option>
            </select>
          </div>

          <button type="submit">Submit</button>
        </form>
      </div>
    </div>
  );
}

export default Loans;