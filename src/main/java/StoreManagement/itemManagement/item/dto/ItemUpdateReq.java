package StoreManagement.itemManagement.item.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemUpdateReq {
    @Size(min = 2, message = "Item name must be at least 2 characters")
    private String itemName;

    @Size(min = 10, message = "Description must be at least 10 characters")
    private String description;

    private BigDecimal price;

    private Integer initialQuantity;

    private Integer categoryId;
}
