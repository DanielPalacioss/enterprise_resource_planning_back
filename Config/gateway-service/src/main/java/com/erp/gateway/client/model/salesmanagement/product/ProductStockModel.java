package com.erp.gateway.client.model.salesmanagement.product;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductStockModel {

    private Long id;

    private int quantity;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    private ProductModel product;

}
