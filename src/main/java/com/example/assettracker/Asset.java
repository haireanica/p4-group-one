package com.example.assettracker;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
public class Asset {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String assetTag;
    private String category;
    private String status;

    // constructors, getters, setters
    public Asset() {}
    public Asset(String assetTag, String category, String status) {
        this.assetTag = assetTag;
        this.category = category;
        this.status = status;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public String getCategory(){
        return category;
    }

    public String getStatus(){
        return status;
    }

    public Long getId(){
        return id;
    }

}
