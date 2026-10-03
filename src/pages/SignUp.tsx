
import "./SignUp.css";

import signupImage from "../assets/signup.avif";

function SignUp() {
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

        <form>
          <div className="form-section">
            <label>Name</label>

            <div className="name-fields">
              <input type="text" placeholder="First" />
              <input type="text" placeholder="Last" />
            </div>
          </div>

          <div className="form-section">
            <label htmlFor="email">Email</label>
            <input id="email" type="email" />
          </div>

          <div className="form-section">
            <label htmlFor="phone">Phone #</label>
            <input id="phone" type="tel" />
          </div>

          <div className="form-section">
            <label htmlFor="address1">Address line 1</label>
            <input id="address1" type="text" />
          </div>

          <div className="form-section">
            <label htmlFor="address2">
              Address line 2 (optional)
            </label>
            <input id="address2" type="text" />
          </div>

          <div className="location-fields">
            <div>
              <label htmlFor="city">City</label>
              <input id="city" type="text" />
            </div>

            <div>
              <label htmlFor="state">State</label>
              <input id="state" type="text" />
            </div>

            <div>
              <label htmlFor="zip">Zip</label>
              <input id="zip" type="text" />
            </div>
          </div>

          <button type="submit">Submit</button>
        </form>
      </div>
    </div>
  );
}

export default SignUp;
