package app.domain;

import java.time.LocalDate;

public class Loan {

    private String loanId;
    private Loanee participantId;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private loanStats loanStatus;
    private Asset assetId;

    public Loan(String loanId, LocalDate checkoutDate, LocalDate dueDate, LocalDate returnDate,Asset assetId,  loanStats loanStatus, Loanee participantId) {
        this.loanId = loanId;
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

    public String getLoanId() {
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

    public void setLoanId(String loanId) {
        this.loanId = loanId;
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
