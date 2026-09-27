package com.example.assettracker;

import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    

    @ManyToMany
    @JoinTable(
        name = "role_permissions",
        joinColumns = @JoinColumn (name = "role_id"),
        inverseJoinColumns = @JoinColumn (name = "permission_id")
    )
    private Set<Permission> permissions;

    public Role() {}
    public Role(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public Long getId(){
        return id;
    }

    public Set<Permission> getPermissions(){
        return permissions;
    }
}
