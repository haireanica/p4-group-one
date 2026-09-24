package app.analytics;

/**
 * Data Transfer Object carrying core high-level system stat counts (assets, loans, returns).
 * Maps to the frontend stat counts endpoint: GET /api/v1/analytics/summary-counts
 */
public record SystemTotalsDTO(
        int totalAssets,
        int availableDevices,
        int activeLoans,
        int returnedDevices,
        int overdueLoans
) {}