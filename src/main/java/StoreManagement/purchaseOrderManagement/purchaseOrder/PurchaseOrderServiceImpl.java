package StoreManagement.purchaseOrderManagement.purchaseOrder;

import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import StoreManagement.purchaseOrderManagement.purchaseOrder.dto.PurchaseOrderRequest;
import StoreManagement.storeManagement.Store;
import StoreManagement.storeManagement.StoreService;
import StoreManagement.utils.CurrentlyLoggedInUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final CurrentlyLoggedInUser loggedInUser;
    private final StoreService storeService;

    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository, CurrentlyLoggedInUser loggedInUser, StoreService storeService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.loggedInUser = loggedInUser;
        this.storeService = storeService;
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
    public PurchaseOrder createPurchaseOrder(PurchaseOrderRequest purchaseOrderRequest) {
        Store store = storeService.utilGetStoreById(purchaseOrderRequest.getStoreId());

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setStore(store);
        purchaseOrder.setCreatedBy(loggedInUser.getUser());
        purchaseOrder.setOrderNumber(generateUniquePurchaseOrderNumber());
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

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
