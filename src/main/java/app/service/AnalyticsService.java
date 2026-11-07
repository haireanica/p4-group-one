package app.service;

import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.List;

import app.analytics.*;
import app.domain.Loan;
import org.springframework.stereotype.Service;

import app.domain.loanStats;
import app.repository.AssetRepository;
import app.repository.LoanRepository;
import app.repository.MaintenanceRepository;

@Service
public class AnalyticsService {

    private final AssetRepository assetRepo;
    private final LoanRepository loanRepo;

    public AnalyticsService(AssetRepository assetRepo, LoanRepository loanRepo, MaintenanceRepository maintRepo) {
        this.assetRepo = assetRepo;
        this.loanRepo = loanRepo;
    }

    // Individual Metric Endpoints for Top Cards
    public long getTotalLoans() {
        return loanRepo.count();
    }

    public long getActiveLoans() {
        return loanRepo.countByLoanStatus(loanStats.CHECKED_OUT);
    }

    public long getReturnedLoans() {
        return loanRepo.countByLoanStatus(loanStats.CHECKED_IN);
    }

    public long getOverdueLoansCount() {
        return loanRepo.countOverdueLoans();
    }

    // Weekly Bar Chart Endpoint
    public List<LoanReturnTrendDTO> getLoanReturnTrends() {
        List<LoanReturnTrendProjection> projections = loanRepo.getCurrentWeekLoanActivity();

        return projections.stream()
                .map(p -> new LoanReturnTrendDTO(
                        p.getDayOfWeek(),
                        p.getTotalLoanActivity() != null ? p.getTotalLoanActivity() : 0L
                ))
                .toList();
    }

    // Status Endpoints
    public List<DeviceStatusDistributionDTO> getDeviceStatusDistribution() {
        return assetRepo.getDeviceStatusDistribution();
    }

    public List<LoanStatusDistributionDTO> getLoanStatusDistribution() {
        long activeCount = loanRepo.countByLoanStatus(loanStats.CHECKED_OUT);
        long returnedCount = loanRepo.countByLoanStatus(loanStats.CHECKED_IN);
        long overdueCount = loanRepo.countOverdueLoans();

        return List.of(
                new LoanStatusDistributionDTO("Active", activeCount),
                new LoanStatusDistributionDTO("Returned", returnedCount),
                new LoanStatusDistributionDTO("Overdue", overdueCount)
        );
    }

    // Recent Activity Endpoint
    // Recent Activity Endpoint (Anonymized for Privacy)
    public List<RecentActivityDTO> getRecentActivity() {
        List<Loan> recentLoans = loanRepo.findRecentLoans(PageRequest.of(0, 3));

        return recentLoans.stream().map(loan -> {
            String title;
            String description;

            if (loan.getReturnDate() != null) {
                title = "Device Returned";
                description = "Asset #" + loan.getAssetId() + " was checked back in.";
            } else {
                title = "Device Checked Out";
                description = "Asset #" + loan.getAssetId() + " was checked out.";
            }

            // Use the actual date/timestamp or fallback to "Recently"
            String timeAgo = loan.getReturnDate() != null ? loan.getReturnDate().toString() : loan.getCheckoutDate().toString();

            return new RecentActivityDTO(title, description, timeAgo);
        }).toList();
    }

}