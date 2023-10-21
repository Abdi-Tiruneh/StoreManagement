package StoreManagement.itemManagement.item;

import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import StoreManagement.itemManagement.category.Category;
import StoreManagement.itemManagement.category.CategoryService;
import StoreManagement.itemManagement.item.dto.ItemMapper;
import StoreManagement.itemManagement.item.dto.ItemRegistrationReq;
import StoreManagement.itemManagement.item.dto.ItemResponse;
import StoreManagement.itemManagement.item.dto.ItemUpdateReq;
import StoreManagement.userManagement.user.Users;
import StoreManagement.utils.CurrentlyLoggedInUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CurrentlyLoggedInUser currentlyLoggedInUser;
    private final CategoryService categoryService;


    @Override
    public List<ItemResponse> getAllItems() {
        List<Item> items = itemRepository.findAll();
        if (items.isEmpty())
            throw new ResourceNotFoundException("No Items found.");

        return items.stream()
                .map(ItemMapper::toItemResponse)
                .toList();
    }

    @Override
    public ItemResponse getItemById(Long itemId) {
        Item item = utilGetItemById(itemId);
        return ItemMapper.toItemResponse(item);
    }

    @Override
    public ItemResponse createItem(ItemRegistrationReq itemRegistrationReq) {
        Users loggedInUserUser = currentlyLoggedInUser.getUser();
        Category category = categoryService.getCategoryById(itemRegistrationReq.getCategoryId());

        Item item = ItemMapper.convertToEntity(itemRegistrationReq, category, loggedInUserUser);
        item = itemRepository.save(item);
        return ItemMapper.toItemResponse(item);
    }

    @Override
    @Transactional
    public ItemResponse updateItem(Long itemId, ItemUpdateReq itemUpdateReq) {
        Item item = utilGetItemById(itemId);
        if (itemUpdateReq.getCategoryId() != null) {
            Category category = categoryService.getCategoryById(itemUpdateReq.getCategoryId());
            item.setCategory(category);
        }

        if (itemUpdateReq.getItemName() != null)
            item.setItemName(itemUpdateReq.getItemName());

        if (itemUpdateReq.getDescription() != null)
            item.setDescription(itemUpdateReq.getDescription());

        if (itemUpdateReq.getPrice() != null)
            item.setPrice(itemUpdateReq.getPrice());

        if (itemUpdateReq.getInitialQuantity() != null)
            item.setInitialQuantity(itemUpdateReq.getInitialQuantity());

        item = itemRepository.save(item);
        return ItemMapper.toItemResponse(item);
    }

    @Override
    public void deleteItem(Long itemId) {
        if (!itemRepository.existsById(itemId))
            throw new ResourceNotFoundException("Item not found with ID: " + itemId);

        itemRepository.deleteById(itemId);
    }

    @Override
    public Item utilGetItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + itemId));
    }
}
