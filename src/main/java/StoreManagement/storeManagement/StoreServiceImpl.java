package StoreManagement.storeManagement;

import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import StoreManagement.storeManagement.dto.StoreMapper;
import StoreManagement.storeManagement.dto.StoreRegistrationReq;
import StoreManagement.storeManagement.dto.StoreResponse;
import StoreManagement.storeManagement.dto.StoreUpdateReq;
import StoreManagement.utils.CurrentlyLoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final CurrentlyLoggedInUser currentlyLoggedInUser;


    //Todo: openingDate(registrationReq.getOpeningDate() != null ? registrationReq.getOpeningDate() : LocalDate.now())
    @Override
    public StoreResponse createStore(StoreRegistrationReq registrationReq) {
        Store store = Store.builder()
                .storeName(registrationReq.getStoreName())
                .location(registrationReq.getLocation())
                .contactInformation(registrationReq.getContactInformation())
                .openingDate(registrationReq.getOpeningDate() != null ? registrationReq.getOpeningDate() : LocalDate.now())
                .storeType(StoreType.getStoreTypeEnum(registrationReq.getStoreType()))
                .createdBy(currentlyLoggedInUser.getUser())
                .build();

        Store savedStore = storeRepository.save(store);
        return StoreMapper.toStoreResponse(savedStore);
    }

    @Override
    public List<StoreResponse> getStores() {
        List<Store> stores = storeRepository.findAll(Sort.by(Sort.Order.asc("id")));
        if (stores.isEmpty())
            throw new ResourceNotFoundException("No stores found. Please check back later or contact our support team for assistance.");

        return stores.stream().map(StoreMapper::toStoreResponse).toList();
    }

    @Override
    public Store getStoreById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + storeId));
    }

    @Override
    public StoreResponse updateStore(Long id, StoreUpdateReq updateReq) {
        Store store = getById(id);

        if (updateReq.getStoreName() != null)
            store.setStoreName(updateReq.getStoreName());

        if (updateReq.getLocation() != null)
            store.setLocation(updateReq.getLocation());

        if (updateReq.getContactInformation() != null)
            store.setContactInformation(updateReq.getContactInformation());

        if (updateReq.getOpeningDate() != null)
            store.setOpeningDate(updateReq.getOpeningDate());

        if (updateReq.getStoreType() != null)
            store.setStoreType(StoreType.getStoreTypeEnum(updateReq.getStoreType()));

        Store savedStore = storeRepository.save(store);
        return StoreMapper.toStoreResponse(savedStore);
    }

    public Store getById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
    }

}
