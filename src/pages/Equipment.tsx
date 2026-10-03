import "./Equipment.css";
import loginImage from "../assets/login3.avif";
function Equipment() {
  return (
    <div className="equipment-page"
    style={{ backgroundImage: `url(${loginImage})` }}
    >

      {/* Page header */}
      <div className="equipment-header">
        <div>
          <h1>Equipment</h1>
          <p>Manage devices available through the LendIT program.</p>
        </div>

        {/* Add equipment button */}
        <button className="add-equipment-button">
          + Add Equipment
        </button>
      </div>

      {/* Search and filter section */}
      <div className="equipment-controls">

        {/* Search box */}
        <input
          type="text"
          placeholder="Search equipment..."
        />

        {/* Filter button */}
        <button>
          Filter
        </button>

      </div>

      {/* Equipment table */}
      <div className="equipment-table-container">

        <table>

          {/* Table headings */}
          <thead>
            <tr>
              <th>Device ID</th>
              <th>Type</th>
              <th>Status</th>
              <th>Location</th>
              <th>Actions</th>
            </tr>
          </thead>

          {/* Equipment information */}
          <tbody>

            <tr>
              <td>LT-001</td>
              <td>Laptop</td>
              <td>Available</td>
              <td>Center 1</td>
              <td>
                <button>Edit</button>
              </td>
            </tr>

            <tr>
              <td>TB-002</td>
              <td>Tablet</td>
              <td>On Loan</td>
              <td>Center 2</td>
              <td>
                <button>Edit</button>
              </td>
            </tr>

            <tr>
              <td>HS-003</td>
              <td>Hotspot</td>
              <td>Available</td>
              <td>Center 1</td>
              <td>
                <button>Edit</button>
              </td>
            </tr>

          </tbody>

        </table>

      </div>

    </div>
  );
}

export default Equipment;