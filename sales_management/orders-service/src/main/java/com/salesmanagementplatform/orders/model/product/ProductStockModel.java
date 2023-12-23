package com.salesmanagementplatform.orders.model.product;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductStockModel {

    private Long id;

    @NotNull(message = "quantity cannot be null")
    private int quantity;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    private ProductModel product;
}
