package app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import app.domain.Asset;
import app.domain.Inventory;
import app.domain.Loan;
import app.domain.LoanPurpose;
import app.service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    // Request/response records (DTOs) decide exactly what goes over the wire,
    // instead of exposing the JPA entities and everything they link to.
    public record CheckoutRequest(
            String assetId,
            Integer participantId,
            String reason,
            LoanPurpose loanPurpose,
            Integer days
    ) {}

    // The team database has loan rows with no participant and inventories
    // with no center, so every link is null-checked.
    public record LoanResponse(Integer loanId, String assetId, String assetName, String center,
                               Integer participantId, String reason, LocalDate checkoutDate,
                               LocalDate dueDate, LocalDate returnDate, String status) {

        static LoanResponse from(Loan loan) {
            Asset asset = loan.getAssetId();
            Inventory inventory = asset == null ? null : asset.getInventory();
            String center = (inventory == null || inventory.getCenter() == null) ? null : inventory.getCenter().getName();

            Integer participantId = (loan.getParticipantId() == null) ? null : loan.getParticipantId().getParticipantId();
            String status = (loan.getLoanStatus() != null) ? loan.getLoanStatus().name() : null;

            return new LoanResponse(
                    loan.getLoanId(),
                    asset == null ? null : asset.getAssetId(),
                    asset == null ? null : asset.getName(),
                    center,
                    participantId,
                    loan.getReason(),
                    loan.getCheckoutDate(),
                    loan.getDueDate(),
                    loan.getReturnDate(),
                    status
            );
        }
    }

    @GetMapping
    public List<LoanResponse> getAll() {
        return service.findAll().stream().map(LoanResponse::from).toList();
    }

    @PostMapping("/checkout")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse checkout(@RequestBody CheckoutRequest req) {
        return LoanResponse.from(service.checkout(
                req.assetId(),
                req.participantId(),
                req.reason(),
                req.loanPurpose(),
                req.days()
        ));
    }

    @PostMapping("/{loanId}/return")
    public LoanResponse returnLoan(@PathVariable Integer loanId) {
        return LoanResponse.from(service.returnLoan(loanId));
    }
}