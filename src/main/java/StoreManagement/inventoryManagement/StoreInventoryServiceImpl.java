package StoreManagement.inventoryManagement;

import StoreManagement.exceptions.customExceptions.BadRequestException;
import StoreManagement.exceptions.customExceptions.ResourceAlreadyExistsException;
import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import StoreManagement.inventoryManagement.dto.*;
import StoreManagement.itemManagement.item.Item;
import StoreManagement.itemManagement.item.ItemService;
import StoreManagement.storeManagement.Store;
import StoreManagement.storeManagement.StoreService;
import StoreManagement.utils.CurrentlyLoggedInUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreInventoryServiceImpl implements StoreInventoryService {
    private final StoreInventoryRepository storeInventoryRepository;
    private final StoreService storeService;
    private final ItemService itemService;
    private final CurrentlyLoggedInUser currentlyLoggedInUser;
    @Override
    public StoreInventoryResponse createStoreInventory(StoreInventoryReq storeInventoryReq) {
        Long itemId = storeInventoryReq.getItemId();
        Long storeId = storeInventoryReq.getStoreId();

        if (storeInventoryReq.getMaxThreshold() <= storeInventoryReq.getMinThreshold())
            throw new BadRequestException("Max threshold must be greater than min threshold");

        if (storeInventoryRepository.findByStoreStoreIdAndItemItemId(storeId, itemId).isPresent())
            throw new ResourceAlreadyExistsException("The item has already been added to the store's inventory.");

        Store store = storeService.utilGetStoreById(storeId);
        Item item = itemService.utilGetItemById(itemId);

        StoreInventory storeInventory = new StoreInventory();
        storeInventory.setStore(store);
        storeInventory.setItem(item);
        storeInventory.setAddedBy(currentlyLoggedInUser.getUser());
        storeInventory.setQuantity(storeInventoryReq.getQuantity());
        storeInventory.setMinThreshold(storeInventoryReq.getMinThreshold());
        storeInventory.setMaxThreshold(storeInventoryReq.getMaxThreshold());

        storeInventory = storeInventoryRepository.save(storeInventory);
        return StoreInventoryMapper.toStoreInventoryResponse(storeInventory);
    }

    //todo: check min and max conditions
    @Override
    public StoreInventoryResponse adjustInventoryQuantityAfterPurchaseOrder(Long storeInventoryId, StoreInventoryUpdateReq updateReq) {
        StoreInventory storeInventory = retrieveStoreInventory(storeInventoryId);

        if (updateReq.getQuantity() != null)
            storeInventory.setQuantity(updateReq.getQuantity());

        if (updateReq.getMinThreshold() != null)
            storeInventory.setMinThreshold(updateReq.getMinThreshold());

        if (updateReq.getMaxThreshold() != null)
            storeInventory.setMaxThreshold(updateReq.getMaxThreshold());

        storeInventory = storeInventoryRepository.save(storeInventory);
        return StoreInventoryMapper.toStoreInventoryResponse(storeInventory);
    }

    @Override
    public void adjustInventoryQuantityAfterPurchaseOrder(Long storeId, Long itemId, Integer quantity) {
        StoreInventory storeInventory = storeInventoryRepository
                .findByStoreStoreIdAndItemItemId(storeId, itemId).get();

        int newQuantity = storeInventory.getQuantity() + quantity;
        storeInventory.setQuantity(newQuantity);
        storeInventoryRepository.save(storeInventory);

        if (newQuantity >= storeInventory.getMaxThreshold())
            monitorInventoryThresholdAndSendNotification(storeInventory);
    }

    @Override
    @Transactional
    public StoreInventoryResponse processItemSaleFromInventory(Long storeInventoryId, ItemSaleFromInventoryReq itemSaleFromInventoryReq) {
        StoreInventory storeInventory = retrieveStoreInventory(storeInventoryId);

        int availableQuantity = storeInventory.getQuantity();
        int requestedQuantity = itemSaleFromInventoryReq.getQuantity();

        if (availableQuantity < requestedQuantity)
            throw new BadRequestException("Sale cannot be completed. The requested quantity exceeds the available stock.");

        int updatedQuantity = availableQuantity - requestedQuantity;
        storeInventory.setQuantity(updatedQuantity);
        storeInventory = storeInventoryRepository.save(storeInventory);

        if (updatedQuantity <= storeInventory.getMinThreshold())
            monitorInventoryThresholdAndSendNotification(storeInventory);

        return StoreInventoryMapper.toStoreInventoryResponse(storeInventory);
    }

    @Override
    public void monitorInventoryThresholdAndSendNotification(StoreInventory storeInventory) {

    }

    @Override
    public List<StoreInventoryResponse> getAllInventoryByStoreId(Long storeId) {
        List<StoreInventory> storeInventoryItems = storeInventoryRepository.findByStoreStoreId(storeId);

        return storeInventoryItems.stream()
                .map(StoreInventoryMapper::toStoreInventoryResponse)
                .toList();
    }

    @Override
    public StoreInventoryResponse getStoreInventoryById(Long storeInventoryId) {
        StoreInventory storeInventory = retrieveStoreInventory(storeInventoryId);
        return StoreInventoryMapper.toStoreInventoryResponse(storeInventory);
    }

    @Override
    public void deleteStoreInventory(Long storeInventoryId) {
        if (!storeInventoryRepository.existsById(storeInventoryId))
            throw new ResourceNotFoundException("Store Inventory not found with ID: " + storeInventoryId);

        storeInventoryRepository.deleteById(storeInventoryId);
    }

    private StoreInventory retrieveStoreInventory(Long storeInventoryId) {
        return storeInventoryRepository.findById(storeInventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Store Inventory not found with ID: " + storeInventoryId));
    }

}
