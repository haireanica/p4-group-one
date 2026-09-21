package app.domain;

public class Inventory {

    //attributes
    private String inventoryId;
    private final int assets;

    //Constructor
    public Inventory(String inventoryId, int assets) {
        this.inventoryId = inventoryId;
        this.assets = assets;
    }

    public String getInventoryId() {
        return inventoryId;
    }
    public int getAssets() {
        return assets;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    //public availableAsets();

   //public totalAssets(){}


    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId='" + inventoryId + '\'' +
                ", assets=" + assets +
                '}';
    }
}
