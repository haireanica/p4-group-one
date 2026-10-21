package app.analytics;

import java.time.LocalDate;

/**
 * Data Transfer Object tracking comparative device checkouts and check-ins over time.
 * Maps to the frontend two-line chart endpoint: GET /api/v1/analytics/loan-return-trends
 */
public record LoanReturnTrendDTO(LocalDate date, long loansCount, long returnsCount) {}