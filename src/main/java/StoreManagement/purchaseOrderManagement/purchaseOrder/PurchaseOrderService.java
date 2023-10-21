package StoreManagement.purchaseOrderManagement.purchaseOrder;

import StoreManagement.purchaseOrderManagement.purchaseOrder.dto.PurchaseOrderRequest;

import java.util.List;

public interface PurchaseOrderService {
    List<PurchaseOrder> getAllPurchaseOrders();
    PurchaseOrder getPurchaseOrderById(Long orderId);
    PurchaseOrder createPurchaseOrder(PurchaseOrderRequest purchaseOrderRequest);
    PurchaseOrder updatePurchaseOrder(Long orderId, PurchaseOrderRequest purchaseOrderRequest);
    void deletePurchaseOrder(Long orderId);
}
