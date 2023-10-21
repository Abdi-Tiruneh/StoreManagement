package StoreManagement.storeManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByLocationAndStoreTypeAndOpeningDateAndStoreName(
            String location, StoreType storeType, LocalDate openingDate, String storeName);
}
