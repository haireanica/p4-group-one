package app.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;



/**
 * The inventory class will be used to keep track of the assets that are available for loan at a specific center in OrlandoLendIT.
 * It will change the center to a Center object, and will change the assets to a list of Asset objects.
 * This will allow for the inventory to be associated with a specific center and assets, and will allow for the inventory to be tracked and managed more easily.
 */
@Entity
@Table(name = "inventory")
public class Inventory {

    /**
     * Class attributes
     *
     * The inventoryId will be the class primary key, and will be used to identify the inventory in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventoryId;

    /**
     * The center attribute is a reference to the Center object that the inventory is associated with.
     * The @OneToOne annotation means that one inventory can be associated with one center.
     */
    @OneToOne
    @JoinColumn(name = "centerId")
    private Center center;

    /**
     * The assets attribute is a list of Asset objects that are associated with the inventory.
     * The @OneToMany annotation means that one inventory can have many assets.
     */
    @OneToMany(mappedBy = "inventory")
    private List<Asset> assets = new ArrayList<>();

    /**
     * The constructors for the Inventory class.
     * The default constructor is protected to prevent instantiation without parameters.
     * The parameterized constructor allows for the creation of an Inventory object with a specific center.
     */
    /**
     * Creates a new Inventory object with the specified center.
     *
     * 
     */
    protected Inventory() {}

    /**
     * Creates a new Inventory object with the specified center.
     *
     * @param center the center to associate with the inventory
     */
    public Inventory(Center center) {
        this.center = center;
    }

    /**
     * Returns the center associated with the inventory.
     * @return the center associated with the inventory
     */
    public Center getCenter() {
        return center;
    }

    /**
     * Sets the center associated with the inventory.
     * @param center the center to associate with the inventory
     * 
     */
    public void setCenter(Center center) {
        this.center = center;
    }

    /**
     * Returns the total number of assets associated with the inventory.
     * @return the total number of assets associated with the inventory
     */
    public int getTotalAssets() {
        return assets.size();
    }



    /**
     * Returns the unique identifier of the inventory.
     * @return the unique identifier of the inventory
     */
    public Integer getInventoryId() {
        return inventoryId;
    }
    /**
     * Returns the list of assets associated with the inventory.
     * @return the list of assets associated with the inventory
     */
    public List<Asset> getAssets() {
        return assets;
    }

    /**
     * Override allows for the attributes of the inventory to be printed in a readable format when the object is printed.
     * returns all attributes of the inventory in a string format.
     */

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
