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
     * The default constructor is protected to prevent instantiation without parameters.
     * It is used by the JPA provider to create instances of the class.
     * 
     */
    protected Center() {}
    /**
     * The constructors for the Center class.
     * The parameterized constructor allows for the creation of a Center object with specific attributes.
     * @param name the name of the center
     * @param address1 the first line of the center's address
     * @param address2 the second line of the center's address
     * @param city the city where the center is located
     * @param state the state where the center is located
     * @param postalCode the postal code of the center's location
     * 
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

    /**
     * Returns the centerId of the center.
     * @return the centerId of the center
     */
    public int getCenterId() {
        return centerId;
    }


    /**
     * Returns the name of the center.
     * @return the name of the center
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the state of the center.
     * @return the state of the center
     */
    public String getState() {
        return state;
    }

    /**
     * Returns the first line of the center's address.
     * @param name the name to set for the center
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the state of the center.
     * @param state the state to set for the center
     *
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Returns the first line of the center's address.
     * @return the first line of the center's address
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Sets the first line of the center's address.
     * @param address1 the first line of the center's address to set
     * 
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * Returns the second line of the center's address.
     * @return the second line of the center's address
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Sets the second line of the center's address.
     * @param address2 the second line of the center's address to set
     * 
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * Returns the city where the center is located.
     * @return the city where the center is located
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city where the center is located.
     * @param city the city to set for the center
     *
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the postal code of the center.
     * @return the postal code of the center
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code of the center.
     * @param postalCode the postal code to set for the center
     * 
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }



    /**
     * Override allows for the attributes of the center to be printed in a readable format when the object is printed.
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
