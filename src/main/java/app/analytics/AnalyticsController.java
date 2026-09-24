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
        // TODO
        return null;
    }

    @GetMapping("/loan-return-trends")
    public List<LoanReturnTrendDTO> getLoanReturnTrends() {
        // TODO
        return null;
    }

    @GetMapping("/device-status")
    public List<DeviceStatusDistributionDTO> getDeviceStatusDistribution() {
        // TODO
        return null;
    }

    @GetMapping("/summary-counts")
    public SystemTotalsDTO getSystemTotals() {
        // TODO
        return null;
    }

    @GetMapping("/overdue-loans")
    public List<OverdueLoansByCenterDTO> getOverdueLoansByCenter() {
        // TODO
        return null;
    }

    @GetMapping("/maintenance-by-center")
    public List<MaintenanceByCenterDTO> getMaintenanceByCenter() {
        // TODO
        return null;
    }
}