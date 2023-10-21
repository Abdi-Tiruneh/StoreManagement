package StoreManagement.itemManagement.item;

import StoreManagement.itemManagement.item.dto.ItemRegistrationReq;
import StoreManagement.itemManagement.item.dto.ItemResponse;
import StoreManagement.itemManagement.item.dto.ItemUpdateReq;
import StoreManagement.utils.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@Tag(name = "ITEMS API. GOOD TO GO")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.getItemById(itemId));
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody @Valid ItemRegistrationReq itemRegistrationReq) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(itemRegistrationReq));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Long itemId, @RequestBody @Valid ItemUpdateReq itemUpdateReq) {
        return ResponseEntity.ok(itemService.updateItem(itemId, itemUpdateReq));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ApiResponse.success("Item deleted successfully");
    }
}
