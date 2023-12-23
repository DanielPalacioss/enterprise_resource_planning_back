package com.salesmanagementplatform.invoices.model.product;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductModel {

    @Min(value = 1, message = "The minimum product number is 1")
    @NotNull(message = "Product Number cannot be null")
    private int productNumber;

    @Size(min = 10, max = 255, message = "Product Reference must be between 10 and 255 characters")
    @NotBlank(message = "Product Reference cannot be blank or null")
    private String productReference;

    @NotNull
    @DecimalMin(value = "0", message = "The minimum product vat is 0")
    private float productVat;

    @DecimalMin(value = "1", message = "The minimum cost price is 1")
    @NotNull(message = "Cost Price cannot be null")
    private float costPrice;

    @DecimalMin(value = "1", message = "The minimum sale price is 1")
    @NotNull(message = "salePrice cannot be null")
    private float salePrice;

    private float earnings;

    @NotNull
    @DecimalMax(value = "100", message = "The discount must be between 1% and 100%")
    @DecimalMin(value = "0", message = "The discount must be between 1% and 100%")
    private float discount;

    private LocalDateTime priceUpdateDate;

    private LocalDateTime updateDate;

    private LocalDateTime creationDate;

    private ProductCategoryModel productCategoryModel;

    private ProductStatusModel productStatus;

    @Min(value = 1, message = "The minimum product quantity is 1")
    private int quantity;
}