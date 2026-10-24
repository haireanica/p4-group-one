package com.example.assettracker;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    // Request/response records (DTOs) decide exactly what goes over the wire,
    // instead of exposing the JPA entities and everything they link to.
    public record CheckoutRequest(String assetId, Integer participantId, LoanReason reason, Integer days) {}

    public record LoanResponse(Integer loanId, String assetId, String assetName, String center,
                               Integer participantId, String reason, LocalDate checkoutDate,
                               LocalDate dueDate, LocalDate returnDate, String status) {

        // The team database has loan rows with no participant and inventories
        // with no center, so every link is null-checked.
        static LoanResponse from(Loan loan) {
            Asset asset = loan.getAsset();
            Inventory inventory = asset == null ? null : asset.getInventory();
            String center = (inventory == null || inventory.getCenter() == null) ? null : inventory.getCenter().getName();
            Integer participantId = loan.getLoanee() == null ? null : loan.getLoanee().getParticipantId();
            String status = loan.isOverdue(LocalDate.now()) ? "OVERDUE" : loan.getLoanStatus();
            return new LoanResponse(loan.getLoanId(),
                    asset == null ? null : asset.getAssetId(), asset == null ? null : asset.getName(), center,
                    participantId, loan.getLoanReason(), loan.getCheckoutDate(),
                    loan.getDueDate(), loan.getReturnDate(), status);
        }
    }

    @GetMapping
    public List<LoanResponse> getAll() {
        return service.findAll().stream().map(LoanResponse::from).toList();
    }

    @PostMapping("/checkout")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse checkout(@RequestBody CheckoutRequest req) {
        return LoanResponse.from(service.checkout(req.assetId(), req.participantId(), req.reason(), req.days()));
    }

    @PostMapping("/{loanId}/return")
    public LoanResponse returnLoan(@PathVariable Integer loanId) {
        return LoanResponse.from(service.returnLoan(loanId));
    }
}
