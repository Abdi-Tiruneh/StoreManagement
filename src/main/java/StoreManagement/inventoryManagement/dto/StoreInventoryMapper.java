package StoreManagement.inventoryManagement.dto;

import StoreManagement.inventoryManagement.StoreInventory;

public class StoreInventoryMapper {
    public static StoreInventoryResponse toStoreInventoryResponse(StoreInventory storeInventory) {

        return StoreInventoryResponse.builder()
                .storeInventoryId(storeInventory.getStoreInventoryId())
                .store(storeInventory.getStore())
                .item(storeInventory.getItem())
                .quantity(storeInventory.getQuantity())
                .threshHold(storeInventory.getThreshHold())
                .createdAt(storeInventory.getCreatedAt())
                .updatedAt(storeInventory.getUpdatedAt())
                .build();
    }
}

