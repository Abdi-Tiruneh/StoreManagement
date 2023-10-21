package StoreManagement.purchaseOrderManagement.orderItem;

import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import StoreManagement.itemManagement.item.ItemService;
import StoreManagement.purchaseOrderManagement.orderItem.dto.OrderItemRegistrationReq;
import StoreManagement.purchaseOrderManagement.orderItem.dto.OrderItemUpdateReq;
import StoreManagement.purchaseOrderManagement.purchaseOrder.PurchaseOrderService;
import StoreManagement.purchaseOrderManagement.supplier.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ItemService itemService;
    private final SupplierService supplierService;
    private final PurchaseOrderService purchaseOrderService;

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem getOrderItemById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order Item not found with ID: " + orderItemId));
    }

    @Override
    public OrderItem createOrderItem(OrderItemRegistrationReq orderItemRegistrationReq) {
        OrderItem orderItem = new OrderItem();
        orderItem.setPurchaseOrder(purchaseOrderService.getPurchaseOrderById(orderItemRegistrationReq.getOrderId()));
        orderItem.setItem(itemService.utilGetItemById(orderItemRegistrationReq.getItemId()));
        orderItem.setSupplier(supplierService.getSupplierById(orderItemRegistrationReq.getSupplierId()));
        orderItem.setQuantity(orderItemRegistrationReq.getQuantity());

        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem updateOrderItem(Long orderItemId, OrderItemUpdateReq updateReq) {
        OrderItem existingOrderItem = getOrderItemById(orderItemId);

        return orderItemRepository.save(existingOrderItem);
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {
        OrderItem existingOrderItem = getOrderItemById(orderItemId);
        orderItemRepository.delete(existingOrderItem);
    }

}

