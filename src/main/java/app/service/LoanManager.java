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

@Service
public class LoanManager {

        /**
         * Class attributes
         * The repository attribute is a reference to the LoanRepository that will be used to persist and
         * retrieve loans from the database. It will be used to perform CRUD operations on the Loan entity.
         */



    private final LoanRepository repository;

    private final AssetRepository assetRepository;
    private final LoaneeRepository loaneeRepository;

    /**
     * Constructor
     */
    public LoanManager(LoanRepository repository, AssetRepository assetRepository, LoaneeRepository loaneeRepository) {
        this.repository = repository;
        this.assetRepository = assetRepository;
        this.loaneeRepository = loaneeRepository;
    }

        /**
         * The save method is used to save a loan to the database.
         * It takes a Loan object as a parameter and returns the saved Loan object.
         * It uses the save method of the LoanRepository to persist the loan to the database.
         */
    public Loan save(Loan loan) {
        return repository.save(loan);
    }

    /**
     * The findById method is used to find a loan by its ID.
     * It takes the loan ID as a parameter and returns the Loan object.
     * It uses the findById method of the LoanRepository to retrieve the loan from the database.
     * If the loan is not found, it throws an IllegalArgumentException with a message indicating
     * that the loan was not found.
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
     * It returns a list of Loan objects.
     */
    public List<Loan> findAll() {
        return repository.findAll();
    }

    /**
     * The checkout method is a transactional method to checkout an asset to a loanee.
     * It validates the asset, loanee, and due date before creating a new loan record.
     * It updates the asset status to CHECKED_OUT and saves the changes to the database.
     */
    @Transactional
    public Loan checkout(
            String assetId,
            Integer participantId,
            LocalDate dueDate
    ) {
        /**
         * Validate asset
         */
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

        /**
         * Validate resident
         */
        Loanee loanee = loaneeRepository.findById(participantId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Resident not found: " + participantId
                        )
                );



        /**
         * Validate due date
         */
        if (dueDate == null ||
                !dueDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Due date must be in the future"
            );
        }

        /**
         * Create Loan
         */
        Loan loan = new Loan (
                LocalDate.now(),
                dueDate,
                null,
                asset,
                loanStats.CHECKED_OUT,
                loanee
        );

        /**
         * Update asset status
         */
        asset.setStatus((assetStatus.CHECKED_OUT));

        /**
         * Save changes
         */
        assetRepository.save(asset);

        return repository.save(loan);
    }

    /**
     * Transactional method to return an asset from a loan
     * It validates the loan and updates the asset status to AVAILABLE
     */
    @Transactional
    public Loan returnAsset(Integer loanId) {

        /**
         * Find the loan
         */
        Loan loan = repository.findById(loanId)
            .orElseThrow(()
            -> new IllegalArgumentException(
                    "Loan not found: " + loanId
            )
        );

        /**
         * Prevent returning a loan that is not checked out
         */
        if (loan.getLoanStatus() != loanStats.CHECKED_OUT) {
            throw new IllegalStateException(
                    "Loan is not currently checked out"
            );
        }

        /**
         * Get the asset associated with the loan
         */
        Asset asset = loan.getAssetId();

        /**
         * Record the return
         */
        loan.setReturnDate(LocalDate.now());
        loan.setLoanStatus(loanStats.CHECKED_IN);

        /**
         * Restore asset availability
         */
        asset.setStatus(assetStatus.AVAILABLE);

        /**
         * Persist changes
         */
        assetRepository.save(asset);

        return repository.save(loan);

    }





}
