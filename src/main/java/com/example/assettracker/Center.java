package com.example.assettracker;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
public class Center {

    @Id
    @Column(name = "centerId")
    private Integer centerId;

    private String name;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    private String city;

    // CHAR, not VARCHAR, in the team schema: fixed-length codes.
    @JdbcTypeCode(SqlTypes.CHAR)
    private String state;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "postal_code")
    private String postalCode;

    public Center() {}

    public Center(Integer centerId, String name, String addressLine1, String addressLine2,
                  String city, String state, String postalCode) {
        this.centerId = centerId;
        this.name = name;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    public Integer getCenterId() { return centerId; }
    public String getName() { return name; }
    public String getAddressLine1() { return addressLine1; }
    public String getAddressLine2() { return addressLine2; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getPostalCode() { return postalCode; }
}
