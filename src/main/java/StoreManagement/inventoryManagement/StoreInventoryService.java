package StoreManagement.inventoryManagement;

import StoreManagement.inventoryManagement.dto.StoreInventoryReq;
import StoreManagement.inventoryManagement.dto.StoreInventoryResponse;
import StoreManagement.inventoryManagement.dto.StoreInventoryUpdateReq;

import java.util.List;

public interface StoreInventoryService {
    StoreInventoryResponse createStoreInventory(StoreInventoryReq storeInventoryReq);

    StoreInventoryResponse updateStoreInventoryQuantity(Long storeInventoryId, StoreInventoryUpdateReq updateReq);

    void updateStoreInventoryQuantity(Long storeId, Long itemId, Integer quantity);

    List<StoreInventoryResponse> getAllInventoryByStoreId(Long storeId);

    StoreInventoryResponse getStoreInventoryById(Long storeInventoryId);

    void deleteStoreInventory(Long storeInventoryId);
}
