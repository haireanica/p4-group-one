package com.example.assettracker;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public Role() {}

    public Role(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public Integer getId(){
        return id;
    }
}
