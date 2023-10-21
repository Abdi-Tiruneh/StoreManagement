package StoreManagement.purchaseOrderManagement.orderItem.dto;

import lombok.Data;

@Data
public class OrderItemUpdateReq {
    private Long orderId;
    private Long itemId;
    private Long supplierId;
    private int quantity;
}
