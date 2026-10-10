package app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.domain.Asset;
import app.domain.Loan;
import app.domain.Loanee;
import app.domain.assetStatus;
import app.domain.loanStats;
import app.repository.AssetRepository;
import app.repository.LoanRepository;
import app.repository.LoaneeRepository;

/**
 * Manages the business logic for loaning assets to residents.
 * This class provides methods for checking out and returning assets,
 * as well as managing the associated loan records.
 */
@Service
public class LoanManager {

        

    private final LoanRepository repository;

    private final AssetRepository assetRepository;
    private final LoaneeRepository loaneeRepository;

    /**
     * Creates a loan manager using the provided repositories.
     *
     * @param repository the loan repository
     * @param assetRepository the asset repository
     * @param loaneeRepository the loanee repository
     */
    public LoanManager(LoanRepository repository, AssetRepository assetRepository, LoaneeRepository loaneeRepository) {
        this.repository = repository;
        this.assetRepository = assetRepository;
        this.loaneeRepository = loaneeRepository;
    }

        /**
         * The save method is used to save a loan to the database.
         * 
         * @param loan the loan to save
         * @return the saved loan
         */
    public Loan save(Loan loan) {
        return repository.save(loan);
    }

    /**
     * The findById method is used to find a loan by its ID.
     * 
     * @param id the ID of the loan to find
     * @return the found loan
     * @throws IllegalArgumentException if no loan exists with the given ID
     */
    public Loan findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Loan not found: " + id
                        )
        );
    }

    /**
     * The findAll method is used to find all loans in the database.
     * 
     * @return a list of all loans
     */
    public List<Loan> findAll() {
        return repository.findAll();
    }

    /**
     * The checkout method is a transactional method to checkout an asset to a loanee.
     * 
     * @param assetId the ID of the asset to checkout
     * @param participantId the ID of the loanee
     * @param dueDate the due date for the loan
     * @return the created loan
     * @throws IllegalArgumentException if the asset or loanee is not found
     * @throws IllegalStateException if the asset is not available for checkout
     * @throws IllegalArgumentException if the due date is not in the future
     * 
     */
    @Transactional
    public Loan checkout(
            String assetId,
            Integer participantId,
            LocalDate dueDate
    ) {
        
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

       
        Loanee loanee = loaneeRepository.findById(participantId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Resident not found: " + participantId
                        )
                );



        
        if (dueDate == null ||
                !dueDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Due date must be in the future"
            );
        }

        
        Loan loan = new Loan (
                LocalDate.now(),
                dueDate,
                null,
                asset,
                loanStats.CHECKED_OUT,
                loanee
        );

      
        asset.setStatus((assetStatus.CHECKED_OUT));

        assetRepository.save(asset);

        return repository.save(loan);
    }

    /**
     * Transactional method to return an asset from a loan
     * It validates the loan and updates the asset status to AVAILABLE
     * @param loanId The ID of the loan to return
     * @return The updated Loan object
     */
    @Transactional
    public Loan returnAsset(Integer loanId) {


        Loan loan = repository.findById(loanId)
            .orElseThrow(()
            -> new IllegalArgumentException(
                    "Loan not found: " + loanId
            )
        );

  
        if (loan.getLoanStatus() != loanStats.CHECKED_OUT) {
            throw new IllegalStateException(
                    "Loan is not currently checked out"
            );
        }

        Asset asset = loan.getAssetId();

     
        loan.setReturnDate(LocalDate.now());
        loan.setLoanStatus(loanStats.CHECKED_IN);

  
        asset.setStatus(assetStatus.AVAILABLE);

    
        assetRepository.save(asset);

        return repository.save(loan);

    }





}
