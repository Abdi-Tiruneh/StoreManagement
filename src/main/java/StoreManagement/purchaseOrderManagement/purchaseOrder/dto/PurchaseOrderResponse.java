package StoreManagement.purchaseOrderManagement.purchaseOrder.dto;

import StoreManagement.itemManagement.item.Item;
import StoreManagement.purchaseOrderManagement.purchaseOrder.PurchaseOrderStatus;
import StoreManagement.purchaseOrderManagement.supplier.Supplier;
import StoreManagement.storeManagement.Store;
import StoreManagement.userManagement.user.Users;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PurchaseOrderResponse {
    private Long purchaseOrderId;
    private Store store;
    private Item item;
    private Supplier supplier;
    private Users orderedBy;
    private int quantity;
    private String orderNumber;
    private PurchaseOrderStatus purchaseOrderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
