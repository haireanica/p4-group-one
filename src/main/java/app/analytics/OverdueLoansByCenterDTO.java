package app.analytics;

/**
 * Data Transfer Object tracking overdue loans grouped by community center location.
 * Maps to the frontend metric card endpoint: GET /api/v1/analytics/overdue-loans
 */
public record OverdueLoansByCenterDTO(String centerName, int overdueCount) {}