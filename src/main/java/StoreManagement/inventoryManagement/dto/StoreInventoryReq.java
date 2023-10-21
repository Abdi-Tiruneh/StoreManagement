package StoreManagement.inventoryManagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StoreInventoryReq {
    @NotNull(message = "Store is required")
    private Long storeId;

    @NotNull(message = "Item is required")
    private Long itemId;

    @NotNull(message = "Initial Quantity is required")
    private int quantity;

    @NotNull(message = "ThreshHold is required for notification")
    private int threshHold;

}