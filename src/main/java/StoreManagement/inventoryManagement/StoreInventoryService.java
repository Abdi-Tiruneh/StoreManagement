package StoreManagement.inventoryManagement;

import StoreManagement.inventoryManagement.dto.StoreInventoryReq;
import StoreManagement.inventoryManagement.dto.StoreInventoryResponse;
import StoreManagement.inventoryManagement.dto.StoreInventoryUpdateReq;

import java.util.List;

public interface StoreInventoryService {
    List<StoreInventoryResponse> getAllStoreInventory();

    StoreInventoryResponse getStoreInventoryById(Long storeInventoryId);

    StoreInventoryResponse createStoreInventory(StoreInventoryReq storeInventoryReq);

    StoreInventoryResponse updateStoreInventory(Long storeInventoryId, StoreInventoryUpdateReq updateReq);

    void deleteStoreInventory(Long storeInventoryId);
}
