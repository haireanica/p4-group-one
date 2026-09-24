package app.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventory")
public class Inventory {

    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventoryId;

    @OneToOne
    @JoinColumn(name = "centerId")
    private Center center;

    @OneToMany(mappedBy = "inventory")
    private List<Asset> assets = new ArrayList<>();


    protected Inventory() {}

    //Constructor
    public Inventory(Center center) {
        this.center = center;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public int getTotalAssets() {
        return assets.size();
    }



    public Integer getInventoryId() {
        return inventoryId;
    }
    public List<Asset> getAssets() {
        return assets;
    }

   @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId='" + inventoryId +
                ", centerId=" +
                (center != null ? center.getCenterId() : null) +
                ", assets=" + assets.size() +
                '}';
    }
}
