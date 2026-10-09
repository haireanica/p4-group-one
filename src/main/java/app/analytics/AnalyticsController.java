package app.analytics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Handles analytics and reporting endpoints for the system.
 * Currently designed as routing stubs until database schema finalization.
 */

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    @GetMapping("/devices-by-type")
    public List<DeviceByTypeDTO> getDevicesByType() {
        return List.of(
          new DeviceByTypeDTO("Chromebook", 80),
                new DeviceByTypeDTO("IPad", 40)
        );
    }

    @GetMapping("/loan-return-trends")
    public List<LoanReturnTrendDTO> getLoanReturnTrends() {
        return List.of(
                new LoanReturnTrendDTO("2026-09-01", 12, 8),
                new LoanReturnTrendDTO("2026-09-02", 15, 11)
        );
    }

    @GetMapping("/device-status")
    public List<DeviceStatusDistributionDTO> getDeviceStatusDistribution() {
        return List.of(
                new DeviceStatusDistributionDTO("AVAILABLE", 35),
                new DeviceStatusDistributionDTO("ASSIGNED", 105),
                new DeviceStatusDistributionDTO("IN_MAINTENANCE", 15),
                new DeviceStatusDistributionDTO("RETIRED", 27)
        );
    }

    @GetMapping("/summary-counts")
    public SystemTotalsDTO getSystemTotals() {
        return new SystemTotalsDTO(150, 35, 105, 10, 6);
    }

    @GetMapping("/overdue-loans")
    public List<OverdueLoansByCenterDTO> getOverdueLoansByCenter() {
        return List.of(
                new OverdueLoansByCenterDTO("Downtown Center", 4),
                new OverdueLoansByCenterDTO("Winter Park Center", 2)
        );
    }

    @GetMapping("/maintenance-by-center")
    public List<MaintenanceByCenterDTO> getMaintenanceByCenter() {
        return List.of(
                new MaintenanceByCenterDTO("Downtown Center", 5),
                new MaintenanceByCenterDTO("Winter Park Center Center", 7)
        );
    }
}
