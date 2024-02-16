package com.erp.salesmanagement.model.order;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductDetails {

    @NotNull
    private int productNumber;

    @Size(min = 10, max = 255, message = "Product Reference must be between 10 and 255 characters")
    @NotBlank(message = "Product Reference cannot be blank or null")
    private String productReference;

    @DecimalMin(value = "1", message = "The minimum unit price is 1")
    @NotNull(message = "unit price cannot be null")
    private float unitPrice;

    @Min(value = 1, message = "The minimum units is 1")
    @NotNull(message = "units cannot be null")
    private int units;

    @DecimalMax(value = "100", message = "The discount must be between 1% and 100%")
    @DecimalMin(value = "0", message = "The discount must be between 1% and 100%")
    private float discount;

    private float productVat;

    @DecimalMin(value = "1", message = "The minimum subTotal is 1")
    @NotNull(message = "The subTotal cannot be null")
    private Double subtotal;

    @DecimalMin(value = "1", message = "The minimum total is 1")
    @NotNull(message = "The total cannot be null")
    private Double total;
}
