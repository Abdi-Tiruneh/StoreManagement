package StoreManagement.inventoryManagement;

import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import StoreManagement.inventoryManagement.dto.StoreInventoryMapper;
import StoreManagement.inventoryManagement.dto.StoreInventoryReq;
import StoreManagement.inventoryManagement.dto.StoreInventoryResponse;
import StoreManagement.inventoryManagement.dto.StoreInventoryUpdateReq;
import StoreManagement.itemManagement.item.Item;
import StoreManagement.itemManagement.item.ItemService;
import StoreManagement.storeManagement.Store;
import StoreManagement.storeManagement.StoreService;
import StoreManagement.utils.CurrentlyLoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreInventoryServiceImpl implements StoreInventoryService {
    private final StoreInventoryRepository storeInventoryRepository;
    private final CurrentlyLoggedInUser currentlyLoggedInUser;
    private final StoreService storeService;
    private final ItemService itemService;


    @Override
    public List<StoreInventoryResponse> getAllStoreInventory() {
        List<StoreInventory> storeInventoryItems = storeInventoryRepository.findAll();
        if (storeInventoryItems.isEmpty())
            throw new ResourceNotFoundException("No Store Inventory found.");

        return storeInventoryItems.stream()
                .map(StoreInventoryMapper::toStoreInventoryResponse)
                .toList();
    }

    @Override
    public StoreInventoryResponse getStoreInventoryById(Long storeInventoryId) {
        StoreInventory storeInventory = utilGetStoreInventoryById(storeInventoryId);

        return StoreInventoryMapper.toStoreInventoryResponse(storeInventory);
    }

    @Override
    public StoreInventoryResponse createStoreInventory(StoreInventoryReq storeInventoryReq) {
        Store store = storeService.utilGetStoreById(storeInventoryReq.getStoreId());
        Item item = itemService.utilGetItemById(storeInventoryReq.getItemId());

        StoreInventory storeInventory = new StoreInventory();
        storeInventory.setStore(store);
        storeInventory.setItem(item);
        storeInventory.setQuantity(storeInventoryReq.getQuantity());
        storeInventory.setThreshHold(storeInventoryReq.getThreshHold());

        storeInventory = storeInventoryRepository.save(storeInventory);
        return StoreInventoryMapper.toStoreInventoryResponse(storeInventory);
    }

    @Override
    public StoreInventoryResponse updateStoreInventory(Long storeInventoryId, StoreInventoryUpdateReq updateReq) {
        StoreInventory storeInventory = utilGetStoreInventoryById(storeInventoryId);

        if (updateReq.getQuantity() != null)
            storeInventory.setQuantity(updateReq.getQuantity());

        if (updateReq.getThreshHold() != null)
            storeInventory.setThreshHold(updateReq.getThreshHold());

        storeInventory = storeInventoryRepository.save(storeInventory);
        return StoreInventoryMapper.toStoreInventoryResponse(storeInventory);
    }


    @Override
    public void deleteStoreInventory(Long storeInventoryId) {
        if (!storeInventoryRepository.existsById(storeInventoryId))
            throw new ResourceNotFoundException("Store Inventory not found with ID: " + storeInventoryId);

        storeInventoryRepository.deleteById(storeInventoryId);
    }


    private StoreInventory utilGetStoreInventoryById(Long storeInventoryId) {
        return storeInventoryRepository.findById(storeInventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Store Inventory not found with ID: " + storeInventoryId));
    }

}
