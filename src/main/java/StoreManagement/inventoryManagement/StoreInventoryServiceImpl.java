package StoreManagement.inventoryManagement;

import StoreManagement.exceptions.customExceptions.BadRequestException;
import StoreManagement.exceptions.customExceptions.ResourceAlreadyExistsException;
import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import StoreManagement.inventoryManagement.dto.StoreInventoryMapper;
import StoreManagement.inventoryManagement.dto.StoreInventoryReq;
import StoreManagement.inventoryManagement.dto.StoreInventoryResponse;
import StoreManagement.inventoryManagement.dto.StoreInventoryUpdateReq;
import StoreManagement.itemManagement.item.Item;
import StoreManagement.itemManagement.item.ItemService;
import StoreManagement.storeManagement.Store;
import StoreManagement.storeManagement.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreInventoryServiceImpl implements StoreInventoryService {
    private final StoreInventoryRepository storeInventoryRepository;
    private final StoreService storeService;
    private final ItemService itemService;
    @Override
    public StoreInventoryResponse createStoreInventory(StoreInventoryReq storeInventoryReq) {
        Long itemId = storeInventoryReq.getItemId();
        Long storeId = storeInventoryReq.getStoreId();

        if (storeInventoryReq.getMaxThreshold() < storeInventoryReq.getMinThreshold())
            throw new BadRequestException("Max threshold must be greater than or equal to min threshold");

        if (storeInventoryRepository.existsByStoreStoreIdAndItemItemId(storeId, itemId))
            throw new ResourceAlreadyExistsException("The item has already been added to the store's inventory.");

        Store store = storeService.utilGetStoreById(storeId);
        Item item = itemService.utilGetItemById(itemId);

        StoreInventory storeInventory = new StoreInventory();
        storeInventory.setStore(store);
        storeInventory.setItem(item);
        storeInventory.setQuantity(storeInventoryReq.getQuantity());
        storeInventory.setMinThreshold(storeInventoryReq.getMinThreshold());
        storeInventory.setMaxThreshold(storeInventoryReq.getMaxThreshold());

        storeInventory = storeInventoryRepository.save(storeInventory);
        return StoreInventoryMapper.toStoreInventoryResponse(storeInventory);
    }

    //todo: check min and max conditions
    @Override
    public StoreInventoryResponse updateStoreInventory(Long storeInventoryId, StoreInventoryUpdateReq updateReq) {
        StoreInventory storeInventory = utilGetStoreInventoryById(storeInventoryId);

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
    public List<StoreInventoryResponse> getAllInventoryByStoreId(Long storeId) {
        List<StoreInventory> storeInventoryItems = storeInventoryRepository.findByStoreStoreId(storeId);
        if (storeInventoryItems.isEmpty())
            throw new ResourceNotFoundException("No inventory records were found for this store.");

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
