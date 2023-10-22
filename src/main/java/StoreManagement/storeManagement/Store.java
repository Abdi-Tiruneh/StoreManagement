package StoreManagement.storeManagement;

import StoreManagement.userManagement.user.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "stores")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String location;

    @Column(name = "contact_Information", nullable = false,unique = true)
    private String contactInformation;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    @Column(name = "opening_date", nullable = false)
    private LocalDate openingDate;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Users createdBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
