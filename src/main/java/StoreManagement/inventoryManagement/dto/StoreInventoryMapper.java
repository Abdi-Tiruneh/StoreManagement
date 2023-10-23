package StoreManagement.inventoryManagement.dto;

import StoreManagement.inventoryManagement.StoreInventory;
import StoreManagement.itemManagement.item.Item;

public class StoreInventoryMapper {
    public static StoreInventoryResponse toStoreInventoryResponse(StoreInventory storeInventory) {

        Item item = storeInventory.getItem();
        item.setAddedBy(null);

        return StoreInventoryResponse.builder()
                .storeInventoryId(storeInventory.getStoreInventoryId())
//                .store(storeInventory.getStore())
                .item(item)
                .quantity(storeInventory.getQuantity())
                .minThreshHold(storeInventory.getMinThreshold())
                .maxThreshHold(storeInventory.getMaxThreshold())
                .createdAt(storeInventory.getCreatedAt())
                .updatedAt(storeInventory.getUpdatedAt())
                .build();
    }
}

