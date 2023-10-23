package StoreManagement.inventoryManagement.dto;

import StoreManagement.itemManagement.item.Item;
import StoreManagement.storeManagement.Store;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreInventoryResponse {

    private Long storeInventoryId;

    private Store store;

    private Item item;

    private int quantity;

    private int minThreshHold;
    private int maxThreshHold;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
