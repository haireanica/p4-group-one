package com.example.assettracker;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "credentials")
public class User {

    @Id
    @Column(name = "userId")
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String passwordHash;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    public User() {}

    public User(Long id, String userName, String passwordHash, Role role) {
        this.id = id;
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getUserName() { return userName; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }
}
