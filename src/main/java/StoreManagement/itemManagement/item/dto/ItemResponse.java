package StoreManagement.itemManagement.item.dto;

import StoreManagement.userManagement.dto.MinUserResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ItemResponse {

    private Long itemId;

    private String itemName;

    private String description;

    private BigDecimal price;

    private int initialQuantity;

    private String category;

    private MinUserResponse createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
