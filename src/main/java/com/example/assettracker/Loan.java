package com.example.assettracker;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private Integer loanId;

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

    // Stored as text: the team database already holds free-text reasons
    // ("Personal use"), and an enum field would crash on reading them.
    // New loans can still only be created with a LoanReason (see LoanService).
    @Column(name = "reason")
    private String loanReason;

    public Loan() {}

    public Loan(Asset asset, Loanee loanee, LocalDate checkoutDate, LocalDate dueDate,
                LocalDate returnDate, String loanStatus, String loanReason) {
        this.asset = asset;
        this.loanee = loanee;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.loanStatus = loanStatus;
        this.loanReason = loanReason;
    }

    public Integer getLoanId() { return loanId; }
    public Asset getAsset() { return asset; }
    public Loanee getLoanee() { return loanee; }
    public LocalDate getCheckoutDate() { return checkoutDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public String getLoanStatus() { return loanStatus; }
    public String getLoanReason() { return loanReason; }

    public void markReturned(LocalDate returnDate) {
        this.returnDate = returnDate;
        this.loanStatus = "RETURNED";
    }

    // Overdue is derived, not stored: a stored flag would go stale overnight.
    public boolean isOverdue(LocalDate today) {
        return "ACTIVE".equals(loanStatus) && dueDate != null && dueDate.isBefore(today);
    }
}
