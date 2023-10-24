package StoreManagement.purchaseOrderManagement.purchaseOrder;

import StoreManagement.purchaseOrderManagement.purchaseOrder.dto.PurchaseOrderRequest;
import StoreManagement.utils.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase-orders")
@Tag(name = "Purchase Order API.")
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> getAllPurchaseOrders() {
        return ResponseEntity.ok(purchaseOrderService.getAllPurchaseOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(purchaseOrderService.getPurchaseOrderById(orderId));
    }

    @PostMapping
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(@RequestBody @Valid PurchaseOrderRequest purchaseOrderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseOrderService.createPurchaseOrder(purchaseOrderRequest));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<PurchaseOrder> updatePurchaseOrder(@PathVariable Long orderId, @RequestBody PurchaseOrderRequest purchaseOrderRequest) {
        return ResponseEntity.ok(purchaseOrderService.updatePurchaseOrder(orderId, purchaseOrderRequest));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> deletePurchaseOrder(@PathVariable Long orderId) {
        purchaseOrderService.deletePurchaseOrder(orderId);
        return ApiResponse.success("Supplier Detail deleted successfully");
    }
}

