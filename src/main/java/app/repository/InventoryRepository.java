package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.domain.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> { 

}
