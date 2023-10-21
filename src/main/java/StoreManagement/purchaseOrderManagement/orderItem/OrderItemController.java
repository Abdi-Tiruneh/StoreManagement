package StoreManagement.purchaseOrderManagement.orderItem;

import StoreManagement.purchaseOrderManagement.orderItem.dto.OrderItemRegistrationReq;
import StoreManagement.purchaseOrderManagement.orderItem.dto.OrderItemUpdateReq;
import StoreManagement.utils.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
@Tag(name = "Purchase Order API.")
public class OrderItemController {
    private final OrderItemService orderItemService;


    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemService.getAllOrderItems());
    }

    @GetMapping("/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long orderItemId) {
        return ResponseEntity.ok(orderItemService.getOrderItemById(orderItemId));
    }

    @PostMapping
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItemRegistrationReq registrationReq) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItemService.createOrderItem(registrationReq));
    }

    @PutMapping("/{orderItemId}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long orderItemId, @RequestBody OrderItemUpdateReq updateReq) {
        return ResponseEntity.ok(orderItemService.updateOrderItem(orderItemId, updateReq));
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<ApiResponse> deleteOrderItem(@PathVariable Long orderItemId) {
        orderItemService.deleteOrderItem(orderItemId);
        return ApiResponse.success("Order Item Detail deleted successfully");
    }
}
