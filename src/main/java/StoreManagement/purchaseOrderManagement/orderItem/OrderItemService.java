package StoreManagement.purchaseOrderManagement.orderItem;

import StoreManagement.purchaseOrderManagement.orderItem.dto.OrderItemRegistrationReq;
import StoreManagement.purchaseOrderManagement.orderItem.dto.OrderItemUpdateReq;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> getAllOrderItems();

    OrderItem getOrderItemById(Long orderItemId);

    OrderItem createOrderItem(OrderItemRegistrationReq orderItemRegistrationReq);

    OrderItem updateOrderItem(Long orderItemId, OrderItemUpdateReq updateReq);

    void deleteOrderItem(Long orderItemId);
}

