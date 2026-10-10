package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.domain.Inventory;

/**
 *Provides database operations for {@link Inventory} records.
 */
public interface InventoryRepository extends JpaRepository<Inventory, Integer> { 
    

}
