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
    public record CheckoutRequest(Long assetId, Long participantId, LoanReason reason, Integer days) {}

    public record LoanResponse(Long loanId, Long assetId, String assetName, String center,
                               Long participantId, LoanReason reason, LocalDate checkoutDate,
                               LocalDate dueDate, LocalDate returnDate, String status) {

        static LoanResponse from(Loan loan) {
            Asset asset = loan.getAsset();
            String center = asset.getInventory() == null ? null : asset.getInventory().getCenter().getName();
            String status = loan.isOverdue(LocalDate.now()) ? "OVERDUE" : loan.getLoanStatus();
            return new LoanResponse(loan.getLoanId(), asset.getAssetId(), asset.getName(), center,
                    loan.getLoanee().getParticipantId(), loan.getLoanReason(), loan.getCheckoutDate(),
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
    public LoanResponse returnLoan(@PathVariable Long loanId) {
        return LoanResponse.from(service.returnLoan(loanId));
    }
}
