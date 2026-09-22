package app.service;

import app.domain.Loan;
import app.domain.Loanee;
import app.domain.loanStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import app.domain.Asset;
import app.domain.assetStatus;
import app.repository.AssetRepository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;


import app.repository.LoanRepository;
import app.repository.LoaneeRepository;

@Service
public class LoanManager {


    private final LoanRepository repository;

    private final AssetRepository assetRepository;
    private final LoaneeRepository loaneeRepository;

    public LoanManager(LoanRepository repository, AssetRepository assetRepository, LoaneeRepository loaneeRepository) {
        this.repository = repository;
        this.assetRepository = assetRepository;
        this.loaneeRepository = loaneeRepository;
    }

    public Loan save(Loan loan) {
        return repository.save(loan);
    }

    public Loan findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Loan not found: " + id
                        )
        );
    }

    public List<Loan> findAll() {
        return repository.findAll();
    }

    public Loan checkout(
            String assetId,
            Integer participantId,
            LocalDate dueDate
    ) {
        // validate asset
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Asset not found: " + assetId
                        )
                );
        if(!asset.isDeployable()) {
            throw new IllegalStateException(
                    "Asset is not available for checkout"
            );
        }

        //validate resident
        Loanee loanee = loaneeRepository.findById(participantId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Resident not found: " + participantId
                        )
                );


        //validate due date
        if (dueDate == null ||
                !dueDate.isAfter(LocalDate.now())) {
            throw new IllegalStateException(
                    "Due date must be in the future"
            );
        }

        //Create Loan
        Loan loan = new Loan (
                LocalDate.now(),
                dueDate,
                null,
                asset,
                loanStats.CHECKED_OUT,
                loanee
        );

        //Update asset status
        asset.setStatus((assetStatus.CHECKED_OUT));

        // Save changes
        assetRepository.save(asset);

        return repository.save(loan);
    }



}
