package StoreManagement.storeManagement;

import StoreManagement.storeManagement.dto.StoreRegistrationReq;
import StoreManagement.storeManagement.dto.StoreResponse;
import StoreManagement.storeManagement.dto.StoreUpdateReq;

import java.util.List;

public interface StoreService {
    StoreResponse createStore(StoreRegistrationReq registrationReq);

    List<StoreResponse> getStores();

    Store getStoreById(Long storeId);

    StoreResponse updateStore(Long id, StoreUpdateReq updateReq);

//     List<Store> searchStores(String location, StoreType storeType, LocalDate openingDate, String storeName);
}
