package app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents an OrlandolendIT lending center where residents can check out assets.
 *  The Center class contains information about the center's name, address, and location.
 * It is used to manage the inventory of assets available for loan at each center.
 * 
 * <p> Stores teh center's identifying and address information </p>
 */
@Entity
@Table(name = "center")
public class Center {

    /**
     * Class attributes
     * The centerId will be the class primary key, and will be used to identify the
     * center in the database.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int centerId;
    private String name;
    @Column(name = "address_line1")
    private String address1;
    @Column(name = "address_line2")
    private String address2;
    private String city;
    private String state;
    @Column(name = "postal_code", length = 5)
    private String postalCode;

    /**
     * The default constructor for the Center class.
     * It is protected to prevent instantiation without parameters.
     */
    protected Center() {}
    /**
     * The constructors for the Center class.
     * The default constructor is protected to prevent instantiation without parameters.
     */
    public Center( String name, String address1,
                  String address2, String city, String state, String postalCode ) {

        this.name = name;
        this.state = state;
        this.address1 = address1;
        this.city = city;
        this.postalCode = postalCode;
        this.address2 = address2;

    }

    /**
     * Getters and Setters
     */

    public int getCenterId() {
        return centerId;
    }


    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getAddress1() {
        return address1;
    }
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    public String getAddress2() {
        return address2;
    }
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }



    /**
     * Overide allows for the attributes of the center to be printed in a readable format when the object is printed.
     * returns all attributes of the center in a string format.
     */
    @Override
    public String toString() {
        return "Center{" +
                "centerId='" + centerId + '\'' +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
