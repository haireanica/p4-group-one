package app.analytics;

/**
 * Data Transfer Object breaking down current hardware conditions based on status.
 * Maps to the frontend pie chart endpoint: GET /api/v1/analytics/device-status
 */
public record DeviceStatusDistributionDTO(app.domain.assetStatus status, long count) {}