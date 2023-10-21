package StoreManagement.purchaseOrderManagement.purchaseOrder.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchaseOrderRequest {

    @NotNull(message = "Store is required")
    private Long storeId;
}
