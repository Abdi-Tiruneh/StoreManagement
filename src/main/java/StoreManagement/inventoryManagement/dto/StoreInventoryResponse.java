package StoreManagement.inventoryManagement.dto;

import StoreManagement.itemManagement.item.Item;
import StoreManagement.storeManagement.Store;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StoreInventoryResponse {

    private Long storeInventoryId;

    private Store store;

    private Item item;

    private int quantity;

    private int threshHold;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
