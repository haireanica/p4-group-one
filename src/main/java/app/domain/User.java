package app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents a system user and their login credentials mapped to the credentials table.
 */
@Entity
@Table(name = "credentials")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Integer id;

    @Column(name = "username", length = 155)
    private String userName;

    @Column(name = "password", length = 155)
    private String passwordHash;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    protected User() {}

    public User(String userName, String passwordHash, Role role) {
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public User(Integer id, String userName, String passwordHash, Role role) {
        this.id = id;
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", role=" + (role != null ? role.getName() : null) +
                '}';
    }
}