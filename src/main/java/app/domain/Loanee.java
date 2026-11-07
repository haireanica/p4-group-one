package app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 - The loanee class will be used to represent a resident who will receive a loan from OrlandoLendIT.

 - It will limit how much personal information is stored about the resident, and will only store the participantId of the loanee.

 */
@Entity
@Table(name = "loanee")
public class Loanee {
    @Id
    @Column(name = "participantId")
    private Integer participantId;
    @Column(name = "firstName", length = 100)
    private String firstName;
    @Column(name = "lastName", length = 100)
    private String lastName;
    @Column(name = "phoneNumber", length = 30)
    private String phoneNumber;
    @Column(name = "address_line1", length = 150)
    private String addressLine1;
    @Column(name = "address_line2", length = 150)
    private String addressLine2;
    @Column(name = "city", length = 100)
    private String city;
    @Column(name = "state", length = 2)
    private String state;
    @Column(name = "postal_code", length = 100)
    private String postalCode;

    protected Loanee() {}

    public Loanee(Integer participantId) {
        this.participantId = participantId;
    }

    public Loanee(Integer participantId, String firstName, String lastName, String phoneNumber,
                  String addressLine1, String addressLine2, String city, String state, String postalCode) {
        this.participantId = participantId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Loanee{" +
                "participantId=" + participantId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}