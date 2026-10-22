package app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
    
/**
 * The loanee class will be used to represent a resident who will receive a loan from OrlandoLendIT.
 * It will limit how much personal information is stored about the resident, and will only store the participantId of the loanee.
 */
@Entity
@Table(name = "loanee")
public class Loanee {



    /**
     * Class attributes
     * The particitpantId will be the class primary key, and will be used to identify the loanee in the database.
     */
    @Id
    private Integer participantId;

    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String phoneNumber;

    /**
     * Constructors
     * The default constructor is protected to prevent instantiation without parameters.
     * The parameterized constructor allows for the creation of a Loanee object with specific attributes.
     * 
     */
    protected Loanee() {}

    /**
     * Creates a new Loanee object with the specified participantId.
     *
     * @param participantId the participantId of the loanee
     * @param firstName the first name of the loanee
     * @param lastName the last name of the loanee
     * @param address1 the first line of the address of the loanee
     * @param address2 the second line of the address of the loanee
     * @param phoneNumber the phone number of the loanee
     */
    public Loanee(Integer participantId, String firstName, String lastName, String address1, String address2, String phoneNumber) {
        this.participantId = participantId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address1 = address1;
        this.address2 = address2;
        this.phoneNumber = phoneNumber;
    }



    /**
     * Returns the participantId of the loanee.
     * @return the participantId of the loanee
     */
    public Integer getParticipantId() {
        return participantId;
    }




    /**
     * Overide allows for the attributes of the loanee to be printed in a readable format when the object is printed.
     * @return returns all attributes of the loanee in a string format.
     */
    @Override
    public String toString() {
        return "Loanee{" +
                "participantId='" + participantId + '\'' +
                '}';
    }
}
