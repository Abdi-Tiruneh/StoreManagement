package StoreManagement.itemManagement.item;

import StoreManagement.itemManagement.item.dto.ItemRegistrationReq;
import StoreManagement.itemManagement.item.dto.ItemResponse;
import StoreManagement.itemManagement.item.dto.ItemUpdateReq;

import java.util.List;

public interface ItemService {
    List<ItemResponse> getAllItems();

    ItemResponse getItemById(Long itemId);

    Item utilGetItemById(Long itemId);

    ItemResponse createItem(ItemRegistrationReq itemRegistrationReq);

    ItemResponse updateItem(Long itemId, ItemUpdateReq itemUpdateReq);

    void deleteItem(Long itemId);
}
