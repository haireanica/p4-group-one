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

    public Loan() {}

    public Loan(Asset asset, Loanee loanee, LocalDate checkoutDate, LocalDate dueDate,
                LocalDate returnDate, String loanStatus) {
        this.asset = asset;
        this.loanee = loanee;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.loanStatus = loanStatus;
    }

    public Long getLoanId() { return loanId; }
    public Asset getAsset() { return asset; }
    public Loanee getLoanee() { return loanee; }
    public LocalDate getCheckoutDate() { return checkoutDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public String getLoanStatus() { return loanStatus; }
}
