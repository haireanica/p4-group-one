package app.repository;

import app.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *Provides database operations for {@link Inventory} records.
 */
public interface InventoryRepository extends JpaRepository<Inventory, Integer> { 
    

}
