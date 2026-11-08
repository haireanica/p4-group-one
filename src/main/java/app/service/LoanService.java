package app.service;

import java.time.LocalDate;
import java.util.List;

import app.domain.*;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import app.repository.AssetRepository;
import app.repository.LoanRepository;
import app.repository.LoaneeRepository;

@Service
public class LoanService {

    private static final int DEFAULT_LOAN_DAYS = 14;

    private final LoanRepository loans;
    private final AssetRepository assets;
    private final LoaneeRepository loanees;
    private final MaintenanceManager maintenanceManager;

    public LoanService(LoanRepository loans, AssetRepository assets, LoaneeRepository loanees, MaintenanceManager maintenanceManager) {
        this.loans = loans;
        this.assets = assets;
        this.loanees = loanees;
        this.maintenanceManager = maintenanceManager;
    }

    public List<Loan> findAll() {
        return loans.findAll(Sort.by(Sort.Direction.DESC, "checkoutDate"));
    }

    public Loan findById(Integer id) {
        return loans.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found: " + id));
    }

    @Transactional
    public Loan checkout(String assetId, Integer participantId, String reason, String loanPurpose, Integer days) {

        if (assetId == null || assetId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "AssetID is required.");
        }
        if (participantId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "participantId is required");
        }

        Asset asset = assets.findById(assetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asset not found: " + assetId));

        if (!asset.isDeployable()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Asset is not available for checkout");
        }

        Loanee loanee = loanees.findById(participantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resident not found: " + participantId));

        LocalDate today = LocalDate.now();
        int loanDays = (days == null || days <= 0) ? DEFAULT_LOAN_DAYS : days;

        asset.setStatus(assetStatus.CHECKED_OUT);
        assets.save(asset);

        Loan newLoan = new Loan(
                today,
                today.plusDays(loanDays),
                null,
                asset,
                loanStats.CHECKED_OUT,
                loanee,
                reason,
                loanPurpose
        );

        return loans.save(newLoan);
    }

    @Transactional
    public Loan returnLoan(Integer loanId, String condition, String notes) {
        Loan loan = loans.findById(loanId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found: " + loanId));

        if (loan.getLoanStatus() != loanStats.CHECKED_OUT) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan is not currently checked out");
        }

        loan.setReturnDate(LocalDate.now());
        loan.setLoanStatus(loanStats.CHECKED_IN);

        Asset asset = loan.getAssetId();
        if (asset != null) {
            if (condition != null && !condition.equalsIgnoreCase("good")) {
                String issueTitle = "Returned: " + condition.replace("-", " ");
                if (issueTitle.length() > 40) {
                    issueTitle = issueTitle.substring(0, 40);
                }

                String repairNotesContent = (notes != null && !notes.isBlank()) ? notes : "No additional notes provided.";
                if (repairNotesContent.length() > 100) {
                    repairNotesContent = repairNotesContent.substring(0, 100);
                }

                Maintenance maintenance = new Maintenance(
                        issueTitle,
                        maintStat.IN_PROGRESS,
                        repairNotesContent,
                        LocalDate.now(),
                        null,
                        asset
                );
                maintenanceManager.save(maintenance);

                asset.setStatus(assetStatus.IN_MAINTENANCE);
                assets.save(asset);
            } else {
                asset.setStatus(assetStatus.AVAILABLE);
                assets.save(asset);
            }
        }

        return loans.save(loan);
    }
}