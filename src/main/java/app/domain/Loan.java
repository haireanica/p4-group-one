package app.domain;

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
import jakarta.persistence.Table;



/**
 * The loan class will be used to keep track of the loans that are given to residents by OrlandoLendIT.
 * It will change the resident to a Loanee object, and will change the asset to an Asset object. 
 * This will allow for the loan to be associated with a specific resident and asset, and will allow for the loan to be tracked and managed more easily.
 */
@Entity
@Table(name = "loan")
public class Loan {

    /**
     * Class attributes
     * The loanId will be the class primary key, and will be used to identify the
     * loan in the database.
     */
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

    /**
     * Constructors
     * The default constructor is protected to prevent instantiation without parameters.
     * The parameterized constructor allows for the creation of a Loan object with specific attributes.
     */
    /**
     * The default constructor is protected to prevent instantiation without parameters.
     */
    protected Loan() {}
    /**
     * Creates a new Loan object with the specified attributes.
     *
     * @param checkoutDate The date the loan was checked out.
     * @param dueDate The date the loan is due.
     * @param returnDate The date the loan was returned.
     * @param assetId The asset that was loaned.
     * @param loanStatus The status of the loan.
     * @param participantId The participant who borrowed the asset.
     */
    public Loan( LocalDate checkoutDate, LocalDate dueDate, LocalDate returnDate,
                 Asset assetId,  loanStats loanStatus, Loanee participantId) {
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.assetId = assetId;
        this.loanStatus = loanStatus;
        this.participantId = participantId;
    }

    /**
     * Getters and Setters
     */

    public loanStats getLoanStatus() {
        return loanStatus;
    }

    public Asset getAssetId() {
        return assetId;
    }

    public Loanee getParticipantId() { return participantId; }

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

    /**
     * Override allows for the attributes of the loan to be printed in a readable format when the object is printed.
     * returns all attributes of the loan in a string format.
     */
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
