package StoreManagement.purchaseOrderManagement.purchaseOrder;

import StoreManagement.exceptions.customExceptions.BadRequestException;
import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import StoreManagement.itemManagement.item.Item;
import StoreManagement.itemManagement.item.ItemService;
import StoreManagement.purchaseOrderManagement.purchaseOrder.dto.PurchaseOrderRequest;
import StoreManagement.purchaseOrderManagement.supplier.Supplier;
import StoreManagement.storeManagement.Store;
import StoreManagement.storeManagement.StoreService;
import StoreManagement.utils.CurrentlyLoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final CurrentlyLoggedInUser loggedInUser;
    private final StoreService storeService;
    private final ItemService itemService;

    @Override
    public PurchaseOrder createPurchaseOrder(PurchaseOrderRequest purchaseOrderRequest) {
        // Retrieve the store and item based on the request.
        Store store = storeService.utilGetStoreById(purchaseOrderRequest.getStoreId());
        Item item = itemService.utilGetItemById(purchaseOrderRequest.getItemId());

        // Ensure that a supplier is assigned to the item's category.
        Supplier supplier = item.getCategory().getSupplier();
        if (supplier == null)
            throw new BadRequestException("No supplier is assigned to this item's category");

        // Create a new purchase order.
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setStore(store);
        purchaseOrder.setItem(item);
        purchaseOrder.setSupplier(supplier);
        purchaseOrder.setQuantity(purchaseOrderRequest.getQuantity());
        purchaseOrder.setOrderedBy(loggedInUser.getUser());
        purchaseOrder.setOrderNumber(generateUniquePurchaseOrderNumber());
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        // Save the purchase order in the repository.
        return purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    public PurchaseOrder updatePurchaseOrder(Long orderId, PurchaseOrderRequest updatedPurchaseOrder) {
        PurchaseOrder existingPurchaseOrder = getPurchaseOrderById(orderId);

//        // Update the existing entity with the new data
//        existingPurchaseOrder.setStore(updatedPurchaseOrder.get());
//        existingPurchaseOrder.setCreatedBy(updatedPurchaseOrder.getCreatedBy());
//        existingPurchaseOrder.setOrderNumber(updatedPurchaseOrder.getOrderNumber());
//        existingPurchaseOrder.setPurchaseOrderStatus(updatedPurchaseOrder.getPurchaseOrderStatus());

        return purchaseOrderRepository.save(existingPurchaseOrder);
    }

    @Override
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    @Override
    public PurchaseOrder getPurchaseOrderById(Long orderId) {
        return purchaseOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order not found with ID: " + orderId));
    }

    @Override
    public void deletePurchaseOrder(Long orderId) {
        PurchaseOrder existingPurchaseOrder = getPurchaseOrderById(orderId);
        purchaseOrderRepository.delete(existingPurchaseOrder);
    }

    private String generateUniquePurchaseOrderNumber() {
        // Create a prefix for the purchase order number, "PO"
        String prefix = "PO";

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);

        String uniqueIdentifier = UUID.randomUUID().toString().replaceAll("-", "");

        // Combine the prefix, formatted date and time, and unique identifier to create the purchase order number
        String purchaseOrderNumber = prefix + formattedDateTime + uniqueIdentifier;

        return purchaseOrderNumber;
    }

}
