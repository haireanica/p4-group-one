package app.service;

import app.domain.Asset;
import app.domain.assetStatus;
import app.repository.AssetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


//Create the class to handle all the CRUD Test for AssetManager
public class AssetManagerTest {

    //call upon the repo to use the asset object
    private AssetRepository repo;

    private AssetManager manager;

    @BeforeEach
    void setUp(){
        repo = mock(AssetRepository.class);
        manager = new AssetManager(repo);
    }


    private Asset createAsset(String assetId) {
        return new Asset(assetId, "Fred's Thinkpad 3",assetStatus.AVAILABLE, true,List.of() );
    }




    @Test
    void save() {
        // Arrange: create an asset and configure the mock
        Asset asset = createAsset("Lap100");

        when(repo.save(asset)).thenReturn(asset);

        //Act: Call the method being tested
        Asset result = manager.save(asset);

        //Assert: Check the result and Repo interaction
        Assertions.assertEquals(asset, result);

        verify(repo).save(asset);


    }

    @Test
    void findById() {

        Asset asset = createAsset("Lap100");

        when(repo.findById("Lap100"))
                .thenReturn(Optional.of(asset));

        Asset result = manager.findById("Lap100");

        Assertions.assertEquals("Lap100", result.getAssetId());
    }

    @Test
    void findAll() {
        Asset asset = createAsset("Lap100");

        when(repo.findAll())
                .thenReturn(List.of(asset));

        List<Asset> result = manager.findAll();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Lap100", result.get(0).getAssetId());
    }


    @Test
    void importNewAsset() {
        Asset asset = createAsset("Fred's Thinkpad 3");

        when(repo.existsById("Lap100"))
        .thenReturn(false);

        int added = manager.importAssets(List.of(asset));

        Assertions.assertEquals(1, added);

        verify(repo).save(asset);


    }

    @Test
    void importExistingAsset() {
        Asset existing = createAsset("Lap100");
        Asset newAsset = createAsset("Lap200");


        when(repo.existsById("Lap100"))
        .thenReturn(true);

        when(repo.existsById("Lap200"))
                .thenReturn(false);

        int added = manager.importAssets(List.of(existing, newAsset)
        );

        Assertions.assertEquals(1, added);

        verify(repo, never()).save(existing);
        verify(repo).save(newAsset);


    }

    @Test
    void retiredById() {
        //Arrange: Create an available asset
        Asset asset = createAsset("Lap100");

        when(repo.findById("Lap100"))
                .thenReturn(Optional.of(asset));

        //Act: Retire the asset
        manager.retiredById("Lap100");

        //Assert: Verify the status changed
        Assertions.assertEquals(
                assetStatus.RETIRED,
                asset.getStatus()
        );

        //Verify the repository saved the updated asset
        verify(repo).save(asset);

        //Verify the asset was not deleted
        verify(repo, never()).deleteById("Lap100");
    }

    @Test
    void retireNonexistentAsset() {

        //Arrange
        when(repo.findById("Lap999"))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> manager.retiredById("Lap999")

        );
        verify(repo, never()).save(any(Asset.class));
    }

    @Test
    void isDeployable() {
        Asset asset = createAsset("Lap100");
        Asset asset2 = createAsset("Lap200");
        Asset asset3 = createAsset("Lap300");

        asset.setStatus(assetStatus.AVAILABLE);
        asset2.setStatus(assetStatus.IN_MAINTENANCE);
        asset3.setStatus(assetStatus.RETIRED);

        when(repo.existsById("Lap100"))
        .thenReturn(true);
        when(repo.existsById("Lap200"))
        .thenReturn(false);
        when(repo.existsById("Lap300"))
        .thenReturn(false);



    }


}
