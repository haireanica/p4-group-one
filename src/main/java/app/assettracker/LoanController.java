package app.assettracker;

import java.time.LocalDate;
import java.util.List;

import app.domain.Asset;
import app.domain.Loan;
import app.service.LoanManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanManager loanManager;

    public LoanController(LoanManager loanManager) {
        this.loanManager = loanManager;
    }

    public record LoanResponse(
            Integer loanId,
            String assetId,
            String assetName,
            Integer participantId,
            LocalDate checkoutDate,
            LocalDate dueDate,
            LocalDate returnDate,
            String status
    ) {
        static LoanResponse from(Loan loan) {
            return new LoanResponse(
                    loan.getLoanId(),
                    loan.getAssetId() != null ? loan.getAssetId().getAssetId() : null,
                    loan.getAssetId() != null ? loan.getAssetId().getName() : null,
                    loan.getParticipantId() != null ? loan.getParticipantId().getParticipantId() : null,
                    loan.getCheckoutDate(),
                    loan.getDueDate(),
                    loan.getReturnDate(),
                    loan.getLoanStatus() != null ? loan.getLoanStatus().name() : null
            );
        }
    }

    public record CheckoutRequest(
            String assetId,
            Integer participantId,
            LocalDate dueDate
    ) {}

    @GetMapping
    public List<LoanResponse> getAllLoans() {
        return loanManager.findAll().stream().map(LoanResponse::from).toList();
    }

    @PostMapping("/checkout")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> checkoutAsset(@RequestBody CheckoutRequest request) {
        try {
            Loan loan = loanManager.checkout(
                    request.assetId(),
                    request.participantId(),
                    request.dueDate()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(LoanResponse.from(loan));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{loanId}/return")
    public ResponseEntity<?> returnAsset(@PathVariable Integer loanId) {
        try {
            Loan loan = loanManager.returnAsset(loanId);
            return ResponseEntity.ok(LoanResponse.from(loan));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
