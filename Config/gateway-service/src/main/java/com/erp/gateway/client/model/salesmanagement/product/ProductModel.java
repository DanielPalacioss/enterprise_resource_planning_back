package com.erp.gateway.client.model.salesmanagement.product;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductModel {

    private int productNumber;

    private String productReference;

    private float productVat;

    private float costPrice;

    private float salePrice;

    private float earnings;

    private float discount;

    private LocalDateTime priceUpdateDate;

    private LocalDateTime updateDate;

    private LocalDateTime creationDate;

    private ProductCategoryModel productCategoryModel;

    private ProductStatusModel productStatus;

    private int quantity;
}