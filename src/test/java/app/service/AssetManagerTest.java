package app.service;

import app.domain.Asset;
import app.repository.AssetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Create the class to handle all the CRUD Test for AssetManager
public class AssetManagerTest {

    //call upon the repo to use the asset object
    private AssetRepository repo;

    private AssetManager manager;

    @BeforeEach
    void setUp(){
        repo = new AssetRepository();
        manager = new AssetManager(repo);
    }


    private Asset createAsset(String Lap100) {
        return new Asset(Lap100, "Fred's Thinkpad 3", Available, true, "repaired, fixed, cleared");
    }




    @Test
    void save() {
        Asset asset = createAsset("Fred's Thinkpad 3");
        Assertions.assertTrue(this.manager.save(asset).getDeployable());

    }

    @Test
    void findById() {

        Assertions.assertTrue(this.manager.findById("Lap100").getDeployable());
    }

    @Test
    void findAll() {
        Assertions.assertNotNull(this.manager.findAll());
    }

    @Test
    void rejectDouble() {
        Asset asset = createAsset("Fred's Thinkpad 3");
        Assertions.assertFalse(manager.save(asset).getDeployable());
    }

    @Test
    void 

}
