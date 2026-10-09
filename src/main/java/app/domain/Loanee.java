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

    /**
     * Constructors
     * The default constructor is protected to prevent instantiation without parameters.
     * The parameterized constructor allows for the creation of a Loanee object with a specific participantId.
     */
    protected Loanee() {}

    public Loanee(Integer participantId) {
        this.participantId = participantId;
    }



    /**
     * Getters and Setters
     */

    public Integer getParticipantId() {
        return participantId;
    }




    /**
     * Overide allows for the attributes of the loanee to be printed in a readable format when the object is printed.
     * returns all attributes of the loanee in a string format.
     */
    @Override
    public String toString() {
        return "Loanee{" +
                "participantId='" + participantId + '\'' +
                '}';
    }
}
