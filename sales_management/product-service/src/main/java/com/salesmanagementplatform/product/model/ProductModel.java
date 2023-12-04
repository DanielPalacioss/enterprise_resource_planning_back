package com.salesmanagementplatform.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "product")
public class ProductModel {

    @NotNull(message = "Product Number cannot be null")
    @Id
    private int productNumber;

    @Size(min = 10, max = 255, message = "Product Reference must be between 10 and 255 characters")
    @NotBlank(message = "Product Reference cannot be blank or null")
    @Column(name = "productReference", nullable = false)
    private String productReference;

    @Size(min = 1, message = "The minimum VAT is 1")
    @Column(name = "productVat")
    private float productVat;

    @Size(min = 1, message = "The minimum cost price is 1")
    @NotNull(message = "Cost Price cannot be null")
    @Column(name = "costPrice", nullable = false)
    private float costPrice;

    @Size(min = 1, message = "The minimum sale price is 1")
    @NotNull(message = "salePrice cannot be null")
    @Column(name = "salePrice", nullable = false)
    private float salePrice;

    @Null(message = "Earnings must be null")
    @Column(name = "earnings", nullable = false)
    private float earnings;

    @Size(min = 1, max = 100, message = "The discount must be between 1% and 100%")
    @Column(name = "discount")
    private float discount;

    @Column(name = "priceUpdateDate")
    private LocalDateTime priceUpdateDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private ProductCategoryModel productCategoryModel;

    @ManyToOne
    @JoinColumn(name = "productStatus", nullable = false)
    private ProductStatus productStatus;
}