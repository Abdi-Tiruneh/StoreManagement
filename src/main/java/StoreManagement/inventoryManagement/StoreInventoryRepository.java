package StoreManagement.inventoryManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreInventoryRepository extends JpaRepository<StoreInventory, Long> {
}

