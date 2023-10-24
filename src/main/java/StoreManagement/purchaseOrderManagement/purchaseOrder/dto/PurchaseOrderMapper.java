package StoreManagement.purchaseOrderManagement.purchaseOrder.dto;

import StoreManagement.purchaseOrderManagement.purchaseOrder.PurchaseOrder;
import StoreManagement.userManagement.dto.MinUserResponse;
import StoreManagement.userManagement.user.Users;

public class PurchaseOrderMapper {
    public static PurchaseOrderResponse toPurchaseOrderResponse(PurchaseOrder purchaseOrder) {

        Users user = new Users();
        user.setId(purchaseOrder.getOrderedBy().getId());
        user.setFullName(purchaseOrder.getOrderedBy().getFullName());
        user.setRole(purchaseOrder.getOrderedBy().getRole().getRoleName());

        return PurchaseOrderResponse.builder()
                .purchaseOrderId(purchaseOrder.getPurchaseOrderId())
                .store(item.getItemName())
                .item(item.getDescription())

                .supplier(purchaseOrder.getSupplier())
                .user(user)
                .quantity(purchaseOrder.getQuantity())

                .orderNumber(purchaseOrder.getOrderNumber())
                .purchaseOrderStatus(purchaseOrder.getPurchaseOrderStatus())
                .createdAt(purchaseOrder.getCreatedAt())
                .updatedAt(purchaseOrder.getUpdatedAt())
                .build();
    }
}
