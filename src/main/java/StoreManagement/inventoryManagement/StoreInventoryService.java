package StoreManagement.inventoryManagement;

import StoreManagement.inventoryManagement.dto.StoreInventoryReq;
import StoreManagement.inventoryManagement.dto.StoreInventoryResponse;
import StoreManagement.inventoryManagement.dto.StoreInventoryUpdateReq;

import java.util.List;

public interface StoreInventoryService {
    StoreInventoryResponse createStoreInventory(StoreInventoryReq storeInventoryReq);

    StoreInventoryResponse updateStoreInventory(Long storeInventoryId, StoreInventoryUpdateReq updateReq);

    List<StoreInventoryResponse> getAllInventoryByStoreId(Long storeId);

    StoreInventoryResponse getStoreInventoryById(Long storeInventoryId);

    void deleteStoreInventory(Long storeInventoryId);
}
