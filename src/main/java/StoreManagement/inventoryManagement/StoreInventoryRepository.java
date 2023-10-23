package StoreManagement.inventoryManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreInventoryRepository extends JpaRepository<StoreInventory, Long> {
    boolean existsByStoreStoreIdAndItemItemId(Long storeId, Long itemId);
    List<StoreInventory> findByStoreStoreId(Long storeId);
}

