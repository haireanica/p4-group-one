package app.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "center")
public class Center {

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

    protected Center() {}

    //constructor
    public Center( String name, String address1,
                  String address2, String city, String state, String postalCode ) {

        this.name = name;
        this.state = state;
        this.address1 = address1;
        this.city = city;
        this.postalCode = postalCode;
        this.address2 = address2;

    }

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
