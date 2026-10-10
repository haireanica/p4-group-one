package com.example.assettracker;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

// Business rules for the loan lifecycle live here, not in the controller.
// The controller only translates HTTP <-> Java; this class decides what is allowed.
@Service
public class LoanService {

    private static final int DEFAULT_LOAN_DAYS = 14;

    private final LoanRepository loans;
    private final AssetRepository assets;
    private final LoaneeRepository loanees;

    public LoanService(LoanRepository loans, AssetRepository assets, LoaneeRepository loanees) {
        this.loans = loans;
        this.assets = assets;
        this.loanees = loanees;
    }

    public List<Loan> findAll() {
        return loans.findAll(Sort.by(Sort.Direction.DESC, "checkoutDate"));
    }

    // @Transactional: the new loan row and the asset status change are saved
    // together or not at all, so a device can never end up half checked out.
    @Transactional
    public Loan checkout(Long assetId, Long participantId, LoanReason reason, Integer days) {
        Asset asset = assets.findById(assetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asset " + assetId + " not found"));

        if (!"AVAILABLE".equals(asset.getAssetStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    asset.getName() + " is " + asset.getAssetStatus() + ", not AVAILABLE");
        }
        if (participantId == null || reason == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "participantId and reason are required");
        }

        // Participants are only an opaque number, so registering one on first
        // checkout stores nothing personal.
        Loanee loanee = loanees.findById(participantId)
                .orElseGet(() -> loanees.save(new Loanee(participantId)));

        LocalDate today = LocalDate.now();
        int loanDays = (days == null || days <= 0) ? DEFAULT_LOAN_DAYS : days;

        asset.setAssetStatus("ON_LOAN");
        return loans.save(new Loan(asset, loanee, today, today.plusDays(loanDays), null, "ACTIVE", reason));
    }

    @Transactional
    public Loan returnLoan(Long loanId) {
        Loan loan = loans.findById(loanId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan " + loanId + " not found"));

        if (!"ACTIVE".equals(loan.getLoanStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan " + loanId + " is already returned");
        }

        loan.markReturned(LocalDate.now());
        loan.getAsset().setAssetStatus("AVAILABLE");
        return loan;   // no save() needed: changes to managed entities flush at commit
    }
}
