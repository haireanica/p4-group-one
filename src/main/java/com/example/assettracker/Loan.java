package com.example.assettracker;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loanId")
    private Long loanId;

    @ManyToOne
    @JoinColumn(name = "assetId")
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "participantId")
    private Loanee loanee;

    @Column(name = "checkoutDate")
    private LocalDate checkoutDate;

    @Column(name = "dueDate")
    private LocalDate dueDate;

    @Column(name = "returnDate")
    private LocalDate returnDate;

    @Column(name = "loanStatus")
    private String loanStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason")
    private LoanReason loanReason;

    public Loan() {}

    public Loan(Asset asset, Loanee loanee, LocalDate checkoutDate, LocalDate dueDate,
                LocalDate returnDate, String loanStatus, LoanReason loanReason) {
        this.asset = asset;
        this.loanee = loanee;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.loanStatus = loanStatus;
        this.loanReason = loanReason;
    }

    public Long getLoanId() { return loanId; }
    public Asset getAsset() { return asset; }
    public Loanee getLoanee() { return loanee; }
    public LocalDate getCheckoutDate() { return checkoutDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public String getLoanStatus() { return loanStatus; }
    public LoanReason getLoanReason() { return loanReason; }

    public void markReturned(LocalDate returnDate) {
        this.returnDate = returnDate;
        this.loanStatus = "RETURNED";
    }

    // Overdue is derived, not stored: a stored flag would go stale overnight.
    public boolean isOverdue(LocalDate today) {
        return "ACTIVE".equals(loanStatus) && dueDate != null && dueDate.isBefore(today);
    }
}
