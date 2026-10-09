package com.example.assettracker.analytics;

/**
 * Data Transfer Object comparing hardware repair and maintenance frequency across centers.
 * Maps to the frontend bar chart endpoint: GET /api/v1/analytics/maintenance-by-center
 */
public record MaintenanceByCenterDTO(String centerName, long maintenanceCount) {}