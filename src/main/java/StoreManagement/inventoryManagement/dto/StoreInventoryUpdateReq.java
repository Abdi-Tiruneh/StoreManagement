package StoreManagement.inventoryManagement.dto;

import lombok.Data;

@Data
public class StoreInventoryUpdateReq {

    private Integer quantity;

    private Integer threshHold;
}
