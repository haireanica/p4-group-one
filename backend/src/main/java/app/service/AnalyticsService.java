package app.service;

import app.domain.Asset;
import app.repository.LoaneeRepository;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import app.analytics.*;
import app.domain.Loan;
import org.springframework.stereotype.Service;

import app.domain.loanStats;
import app.repository.AssetRepository;
import app.repository.LoanRepository;

@Service
public class AnalyticsService {

    private final AssetRepository assetRepo;
    private final LoanRepository loanRepo;
    private final LoaneeRepository loaneeRepo;

    public AnalyticsService(AssetRepository assetRepo, LoanRepository loanRepo, LoaneeRepository loaneeRepo) {
        this.assetRepo = assetRepo;
        this.loanRepo = loanRepo;
        this.loaneeRepo = loaneeRepo;
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

    public long getTotalEquipment() {
        return assetRepo.count();
    }

    public long getTotalParticipants() {
        return loaneeRepo.count();
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

            // Get the asset information from the Asset object
            Asset asset = loan.getAssetId();
            String assetName = asset.getName();
            String assetId = asset.getAssetId();

            if (loan.getReturnDate() != null) {
                title = "Device Returned";
                description = assetName + " (Device ID: " + assetId + ") was checked back in.";
            } else {
                title = "Device Checked Out";
                description = assetName + " (Device ID: " + assetId + ") was checked out.";
            }

            // Use the actual date/timestamp or fallback to "Recently"
            String timeAgo = loan.getReturnDate() != null ? loan.getReturnDate().toString() : loan.getCheckoutDate().toString();

            return new RecentActivityDTO(title, description, timeAgo);
        }).toList();
    }

}