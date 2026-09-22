package app.domain;

import java.time.LocalDate;

import jakarta.persistence.*;


@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loanId;

    @ManyToOne
    @JoinColumn(name = "participantId")
    private Loanee participantId;


    private LocalDate checkoutDate;


    private LocalDate dueDate;


    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "loanStatus")
    private loanStats loanStatus;

    @ManyToOne
    @JoinColumn(name = "assetId")
    private Asset assetId;

    protected Loan() {}

    public Loan( LocalDate checkoutDate, LocalDate dueDate, LocalDate returnDate,
                 Asset assetId,  loanStats loanStatus, Loanee participantId) {
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.assetId = assetId;
        this.loanStatus = loanStatus;
        this.participantId = participantId;
    }

    public loanStats getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(loanStats loanStatus) {
        this.loanStatus = loanStatus;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }


    @Override
    public String toString() {
        return "Loan{" +
                "loanId='" + loanId + '\'' +
                ", participantId=" + participantId +
                ", checkoutDate=" + checkoutDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", loanStatus=" + loanStatus +
                ", assetId=" + assetId +
                '}';
    }
}
