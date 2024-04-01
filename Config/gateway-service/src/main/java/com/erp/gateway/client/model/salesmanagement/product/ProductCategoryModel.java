package com.erp.gateway.client.model.salesmanagement.product;

import com.erp.gateway.client.model.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductCategoryModel {

    private Long id;

    private String category;

    private String description;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    private Status status;
}
