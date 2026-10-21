package app.analytics;

import app.repository.AssetRepository;
import app.repository.LoanRepository;
import app.repository.MaintenanceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Handles analytics and reporting endpoints for the system.
 */

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    private final AssetRepository assetRepo;
    private final LoanRepository loanRepo;
    private final MaintenanceRepository maintRepo;

    public AnalyticsController(AssetRepository assets, LoanRepository loans, MaintenanceRepository maintenance) {
        assetRepo = assets;
        loanRepo = loans;
        maintRepo = maintenance;
    }

    @GetMapping("/devices-by-type")
    public List<DeviceByTypeDTO> getDevicesByType() {
        return assetRepo.getAssetsByType();
    }

    @GetMapping("/loan-return-trends")
    public List<LoanReturnTrendDTO> getLoanReturnTrends() {
        return loanRepo.getLoanReturnTrends().stream().map(
                p -> new LoanReturnTrendDTO(
                        p.getDate(), p.getLoansCount(), p.getReturnsCount())
        ).toList();
    }

    @GetMapping("/device-status")
    public List<DeviceStatusDistributionDTO> getDeviceStatusDistribution() {
        return assetRepo.getDeviceStatusDistribution();
    }

    @GetMapping("/summary-counts")
    public SystemTotalsDTO getSystemTotals() {
        return new SystemTotalsDTO(
                assetRepo.count(),
                assetRepo.countByStatus(app.domain.assetStatus.AVAILABLE),
                loanRepo.countByLoanStatus(app.domain.loanStats.CHECKED_OUT),
                loanRepo.countByLoanStatus(app.domain.loanStats.CHECKED_IN),
                loanRepo.countOverdueLoans()
        );
    }

    @GetMapping("/overdue-loans")
    public List<OverdueLoansByCenterDTO> getOverdueLoansByCenter() {
        return loanRepo.getOverdueLoansByCenter();
    }

    @GetMapping("/maintenance-by-center")
    public List<MaintenanceByCenterDTO> getMaintenanceByCenter() {
        return maintRepo.getMaintenanceByCenter();
    }
}