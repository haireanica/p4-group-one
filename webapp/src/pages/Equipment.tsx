import { useEffect, useState } from "react";
import "./Equipment.css";
import loginImage from "../assets/login3.avif";

interface Center {
  centerId: number;
  name: string;
}

interface Inventory {
  inventoryId: number;
  center?: Center;
}

interface Asset {
  assetId: string;
  name: string;
  deviceType: string;
  status: string;
  location?: string;
  inventory?: Inventory;
}

function Equipment() {
  const [assets, setAssets] = useState<Asset[]>([]);
  const [centers, setCenters] = useState<Center[]>([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState<boolean>(true);
  
  // Add Equipment Modal state
  const [isAddModalOpen, setIsAddModalOpen] = useState<boolean>(false);
  const [assetId, setAssetId] = useState("");
  const [name, setName] = useState("");
  const [deviceType, setDeviceType] = useState("Laptop");
  const [status, setStatus] = useState("AVAILABLE");
  const [selectedCenterId, setSelectedCenterId] = useState<number | "">("");
  const [formError, setFormError] = useState("");

  // Edit Equipment Modal state
  const [isEditModalOpen, setIsEditModalOpen] = useState<boolean>(false);
  const [editingAssetId, setEditingAssetId] = useState("");
  const [editName, setEditName] = useState("");
  const [editDeviceType, setEditDeviceType] = useState("");
  const [editStatus, setEditStatus] = useState("AVAILABLE");
  const [editCenterId, setEditCenterId] = useState<number | "">("");
  const [editError, setEditError] = useState("");

  // Filter Modal state
  const [isFilterModalOpen, setIsFilterModalOpen] = useState<boolean>(false);
  const [tempFilterText, setTempFilterText] = useState("");
  const [tempFilterStatus, setTempFilterStatus] = useState("ALL");
  const [tempFilterCenterId, setTempFilterCenterId] = useState<string>("ALL");

  // Applied filter state
  const [appliedFilterText, setAppliedFilterText] = useState("");
  const [appliedFilterStatus, setAppliedFilterStatus] = useState("ALL");
  const [appliedFilterCenterId, setAppliedFilterCenterId] = useState<string>("ALL");

  const fetchData = () => {
    const authHeader = localStorage.getItem("authHeader") || "";
    const headers = {
      "Content-Type": "application/json",
      Authorization: authHeader,
    };

    Promise.all([
      fetch("/api/assets", { headers }).then((res) => (res.ok ? res.json() : [])),
      fetch("/api/centers", { headers }).then((res) => (res.ok ? res.json() : []))
    ])
      .then(([assetData, centerData]) => {
        setAssets(assetData);
        setCenters(centerData);
        if (centerData.length > 0) {
          if (selectedCenterId === "") setSelectedCenterId(centerData[0].centerId);
          if (editCenterId === "") setEditCenterId(centerData[0].centerId);
        }
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching data:", err);
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleAddEquipment = async (e: React.FormEvent) => {
    e.preventDefault();
    setFormError("");

    const authHeader = localStorage.getItem("authHeader") || "";
    const headers = {
      "Content-Type": "application/json",
      Authorization: authHeader,
    };

    try {
      const chosenCenter = centers.find(c => c.centerId === Number(selectedCenterId));
      if (!chosenCenter) throw new Error("Please select a valid center.");

      const invResponse = await fetch("/api/inventories/ensure", {
        method: "POST",
        headers,
        body: JSON.stringify(chosenCenter),
      });

      if (!invResponse.ok) throw new Error("Failed to resolve center inventory.");
      const assignedInventory = await invResponse.json();

      const newAsset = {
        assetId,
        name,
        deviceType,
        status,
        inventory: { inventoryId: assignedInventory.inventoryId }
      };

      const assetResponse = await fetch("/api/assets", {
        method: "POST",
        headers,
        body: JSON.stringify(newAsset),
      });

      if (assetResponse.ok) {
        setAssetId("");
        setName("");
        setDeviceType("Laptop");
        setStatus("AVAILABLE");
        setIsAddModalOpen(false);
        fetchData();
      } else {
        const errorText = await assetResponse.text();
        setFormError(errorText || "Failed to add equipment.");
      }
    } catch (err: any) {
      console.error("Error saving asset:", err);
      setFormError(err.message || "Network error while saving equipment.");
    }
  };

  const openEditModal = (asset: Asset) => {
    setEditingAssetId(asset.assetId);
    setEditName(asset.name || "");
    setEditDeviceType(asset.deviceType || "");
    setEditStatus(asset.status || "AVAILABLE");
    if (asset.inventory?.center?.centerId) {
      setEditCenterId(asset.inventory.center.centerId);
    } else if (centers.length > 0) {
      setEditCenterId(centers[0].centerId);
    }
    setEditError("");
    setIsEditModalOpen(true);
  };

  const handleUpdateEquipment = async (e: React.FormEvent) => {
    e.preventDefault();
    setEditError("");

    const authHeader = localStorage.getItem("authHeader") || "";
    const headers = {
      "Content-Type": "application/json",
      Authorization: authHeader,
    };

    try {
      const chosenCenter = centers.find(c => c.centerId === Number(editCenterId));
      if (!chosenCenter) throw new Error("Please select a valid center.");

      const invResponse = await fetch("/api/inventories/ensure", {
        method: "POST",
        headers,
        body: JSON.stringify(chosenCenter),
      });

      if (!invResponse.ok) throw new Error("Failed to resolve center inventory.");
      const assignedInventory = await invResponse.json();

      const updatedAsset = {
        assetId: editingAssetId,
        name: editName,
        deviceType: editDeviceType,
        status: editStatus,
        inventory: { inventoryId: assignedInventory.inventoryId }
      };

      const assetResponse = await fetch(`/api/assets/${editingAssetId}`, {
        method: "PUT",
        headers,
        body: JSON.stringify(updatedAsset),
      });

      if (assetResponse.ok) {
        setIsEditModalOpen(false);
        fetchData();
      } else {
        const errorText = await assetResponse.text();
        setEditError(errorText || "Failed to update equipment.");
      }
    } catch (err: any) {
      console.error("Error updating asset:", err);
      setEditError(err.message || "Network error while updating equipment.");
    }
  };

  const openFilterModal = () => {
    setTempFilterText(appliedFilterText);
    setTempFilterStatus(appliedFilterStatus);
    setTempFilterCenterId(appliedFilterCenterId);
    setIsFilterModalOpen(true);
  };

  const applyFilters = () => {
    setAppliedFilterText(tempFilterText);
    setAppliedFilterStatus(tempFilterStatus);
    setAppliedFilterCenterId(tempFilterCenterId);
    setIsFilterModalOpen(false);
  };

  const clearFilters = () => {
    setTempFilterText("");
    setTempFilterStatus("ALL");
    setTempFilterCenterId("ALL");
    setAppliedFilterText("");
    setAppliedFilterStatus("ALL");
    setAppliedFilterCenterId("ALL");
    setIsFilterModalOpen(false);
  };

  const filteredAssets = assets.filter((asset) => {
    const quickTerm = searchTerm.toLowerCase();
    const matchesQuickSearch =
      !searchTerm ||
      asset.assetId.toLowerCase().includes(quickTerm) ||
      (asset.deviceType && asset.deviceType.toLowerCase().includes(quickTerm)) ||
      (asset.status && asset.status.toLowerCase().includes(quickTerm)) ||
      (asset.name && asset.name.toLowerCase().includes(quickTerm));

    if (!matchesQuickSearch) return false;

    if (appliedFilterText) {
      const textTerm = appliedFilterText.toLowerCase();
      const matchesText =
        asset.assetId.toLowerCase().includes(textTerm) ||
        (asset.deviceType && asset.deviceType.toLowerCase().includes(textTerm)) ||
        (asset.name && asset.name.toLowerCase().includes(textTerm));
      if (!matchesText) return false;
    }

    if (appliedFilterStatus !== "ALL" && asset.status !== appliedFilterStatus) {
      return false;
    }

    if (appliedFilterCenterId !== "ALL") {
      const assetCenterId = asset.inventory?.center?.centerId?.toString();
      if (assetCenterId !== appliedFilterCenterId) {
        return false;
      }
    }

    return true;
  });

  return (
    <div
      className="equipment-page"
      style={{ backgroundImage: `url(${loginImage})` }}
    >
      <div className="equipment-header">
        <div>
          <h1>Equipment</h1>
          <p>Manage devices available through the LendIT program.</p>
        </div>
        <button
          className="add-equipment-button"
          onClick={() => setIsAddModalOpen(true)}
        >
          + Add Equipment
        </button>
      </div>

      <div className="equipment-controls">
        <input
          type="text"
          placeholder="Search equipment by ID, type, name, or status..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <button onClick={openFilterModal}>
          Filter {(appliedFilterStatus !== "ALL" || appliedFilterCenterId !== "ALL" || appliedFilterText) ? "(Active)" : ""}
        </button>
      </div>

      <div className="equipment-table-container">
        <table>
          <thead>
            <tr>
              <th>Device ID</th>
              <th>Name / Type</th>
              <th>Status</th>
              <th>Location</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr>
                <td colSpan={5} style={{ textAlign: "center" }}>Loading equipment...</td>
              </tr>
            ) : filteredAssets.length === 0 ? (
              <tr>
                <td colSpan={5} style={{ textAlign: "center" }}>No equipment found matching criteria.</td>
              </tr>
            ) : (
              filteredAssets.map((asset) => (
                <tr key={asset.assetId}>
                  <td>{asset.assetId}</td>
                  <td>{asset.deviceType || asset.name}</td>
                  <td>{asset.status}</td>
                  <td>
                    {asset.inventory?.center?.name || 
                     (asset.inventory ? `Inventory #${asset.inventory.inventoryId}` : "Main Center")}
                  </td>
                  <td>
                    <button onClick={() => openEditModal(asset)}>Edit</button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      {/* Add Equipment Modal */}
      {isAddModalOpen && (
        <div className="modal-backdrop">
          <div className="modal-content">
            <h2>Add New Equipment</h2>
            {formError && <p className="error-message">{formError}</p>}
            
            <form onSubmit={handleAddEquipment}>
              <div className="form-group">
                <label>Device ID</label>
                <input
                  type="text"
                  placeholder="e.g. LT-105"
                  value={assetId}
                  onChange={(e) => setAssetId(e.target.value)}
                  required
                />
              </div>

              <div className="form-group">
                <label>Asset Name / Description</label>
                <input
                  type="text"
                  placeholder="e.g. Dell Latitude Laptop"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  required
                />
              </div>

              <div className="form-group">
                <label>Device Type</label>
                <input
                  type="text"
                  placeholder="e.g. Laptop, Tablet, Hotspot"
                  value={deviceType}
                  onChange={(e) => setDeviceType(e.target.value)}
                  required
                />
              </div>

              <div className="form-group">
                <label>Initial Status</label>
                <select
                  value={status}
                  onChange={(e) => setStatus(e.target.value)}
                >
                  <option value="AVAILABLE">AVAILABLE</option>
                  <option value="CHECKED_OUT">CHECKED_OUT</option>
                  <option value="MAINTENANCE">MAINTENANCE</option>
                  <option value="RETIRED">RETIRED</option>
                </select>
              </div>

              <div className="form-group">
                <label>Center Location</label>
                <select
                  value={selectedCenterId}
                  onChange={(e) => setSelectedCenterId(Number(e.target.value))}
                >
                  {centers.map((center) => (
                    <option key={center.centerId} value={center.centerId}>
                      {center.name}
                    </option>
                  ))}
                </select>
              </div>

              <div className="modal-actions">
                <button
                  type="button"
                  className="cancel-button"
                  onClick={() => setIsAddModalOpen(false)}
                >
                  Cancel
                </button>
                <button type="submit" className="save-button">
                  Save Equipment
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Edit Equipment Modal */}
      {isEditModalOpen && (
        <div className="modal-backdrop">
          <div className="modal-content">
            <h2>Edit Equipment ({editingAssetId})</h2>
            {editError && <p className="error-message">{editError}</p>}
            
            <form onSubmit={handleUpdateEquipment}>
              <div className="form-group">
                <label>Asset Name / Description</label>
                <input
                  type="text"
                  value={editName}
                  onChange={(e) => setEditName(e.target.value)}
                  required
                />
              </div>

              <div className="form-group">
                <label>Device Type</label>
                <input
                  type="text"
                  value={editDeviceType}
                  onChange={(e) => setEditDeviceType(e.target.value)}
                  required
                />
              </div>

              <div className="form-group">
                <label>Status</label>
                <select
                  value={editStatus}
                  onChange={(e) => setEditStatus(e.target.value)}
                >
                  <option value="AVAILABLE">AVAILABLE</option>
                  <option value="CHECKED_OUT">CHECKED_OUT</option>
                  <option value="MAINTENANCE">MAINTENANCE</option>
                  <option value="RETIRED">RETIRED</option>
                </select>
              </div>

              <div className="form-group">
                <label>Center Location</label>
                <select
                  value={editCenterId}
                  onChange={(e) => setEditCenterId(Number(e.target.value))}
                >
                  {centers.map((center) => (
                    <option key={center.centerId} value={center.centerId}>
                      {center.name}
                    </option>
                  ))}
                </select>
              </div>

              <div className="modal-actions">
                <button
                  type="button"
                  className="cancel-button"
                  onClick={() => setIsEditModalOpen(false)}
                >
                  Cancel
                </button>
                <button type="submit" className="save-button">
                  Update Equipment
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Filter Modal */}
      {isFilterModalOpen && (
        <div className="modal-backdrop">
          <div className="modal-content">
            <h2>Filter Equipment</h2>
            
            <div className="form-group">
              <label>Name or Type</label>
              <input
                type="text"
                placeholder="Filter by name or device type..."
                value={tempFilterText}
                onChange={(e) => setTempFilterText(e.target.value)}
              />
            </div>

            <div className="form-group">
              <label>Status</label>
              <select
                value={tempFilterStatus}
                onChange={(e) => setTempFilterStatus(e.target.value)}
              >
                <option value="ALL">All Statuses</option>
                <option value="AVAILABLE">AVAILABLE</option>
                <option value="CHECKED_OUT">CHECKED_OUT</option>
                <option value="MAINTENANCE">MAINTENANCE</option>
                <option value="RETIRED">RETIRED</option>
              </select>
            </div>

            <div className="form-group">
              <label>Center Location</label>
              <select
                value={tempFilterCenterId}
                onChange={(e) => setTempFilterCenterId(e.target.value)}
              >
                <option value="ALL">All Centers</option>
                {centers.map((center) => (
                  <option key={center.centerId} value={center.centerId.toString()}>
                    {center.name}
                  </option>
                ))}
              </select>
            </div>

            <div className="modal-actions">
              <button
                type="button"
                className="cancel-button"
                onClick={clearFilters}
              >
                Clear Filters
              </button>
              <button
                type="button"
                className="save-button"
                onClick={applyFilters}
              >
                Apply Filters
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default Equipment;