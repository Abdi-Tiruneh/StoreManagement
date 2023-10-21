package StoreManagement.storeManagement;

import StoreManagement.storeManagement.dto.StoreRegistrationReq;
import StoreManagement.storeManagement.dto.StoreResponse;
import StoreManagement.storeManagement.dto.StoreUpdateReq;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
@Tag(name = "Stores API.")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<StoreResponse> createStore(@RequestBody @Valid StoreRegistrationReq registrationReq) {
        StoreResponse createdStore = storeService.createStore(registrationReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStore);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreResponse> updateStore(
            @PathVariable Long id, @RequestBody @Valid StoreUpdateReq updateReq) {
        return ResponseEntity.ok(storeService.updateStore(id,updateReq));
    }
    @GetMapping
    public ResponseEntity<List<StoreResponse>> getStores() {
        return ResponseEntity.ok(storeService.getStores());
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<Store>> searchStores(
//            @RequestParam(required = false) String location,
//            @RequestParam(required = false) StoreType storeType,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate openingDate,
//            @RequestParam(required = false) String storeName) {
//        List<Store> stores = storeService.searchStores(location, storeType, openingDate, storeName);
//        return ResponseEntity.ok(stores);
//    }
}

