package app.analytics;

/**
 * Data Transfer Object representing inventory counts grouped by device type.
 * Maps to the frontend bar chart endpoint: GET /api/v1/analytics/devices-by-type
 */
public record DeviceByTypeDTO(String deviceName, int count) {}