package app.controller;

import app.analytics.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.service.AnalyticsService;

import java.util.List;

/**
 * Handles analytics and reporting endpoints for the system.
 */
@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    // Top Metric Card Endpoints
    @GetMapping("/total-loans")
    public long getTotalLoans() {
        return analyticsService.getTotalLoans();
    }

    @GetMapping("/active-loans")
    public long getActiveLoans() {
        return analyticsService.getActiveLoans();
    }

    @GetMapping("/returned-loans")
    public long getReturnedLoans() {
        return analyticsService.getReturnedLoans();
    }

    @GetMapping("/overdue-loans-count")
    public long getOverdueLoansCount() {
        return analyticsService.getOverdueLoansCount();
    }

    @GetMapping("/total-equipment")
    public long getTotalEquipment() {
        return analyticsService.getTotalEquipment();
    }

    @GetMapping("/total-participants")
    public long getTotalParticipants() {
        return analyticsService.getTotalParticipants();
    }

    // Weekly Bar Chart Endpoint
    @GetMapping("/loan-return-trends")
    public List<LoanReturnTrendDTO> getLoanReturnTrends() {
        return analyticsService.getLoanReturnTrends();
    }

    // Status Endpoints
    @GetMapping("/device-status")
    public List<DeviceStatusDistributionDTO> getDeviceStatusDistribution() {
        return analyticsService.getDeviceStatusDistribution();
    }

    @GetMapping("/loan-status-distribution")
    public List<LoanStatusDistributionDTO> getLoanStatusDistribution() {
        return analyticsService.getLoanStatusDistribution();
    }

    // Recent Activity Endpoint
    @GetMapping("/recent-activity")
    public List<RecentActivityDTO> getRecentActivity() {
        return analyticsService.getRecentActivity();
    }

}