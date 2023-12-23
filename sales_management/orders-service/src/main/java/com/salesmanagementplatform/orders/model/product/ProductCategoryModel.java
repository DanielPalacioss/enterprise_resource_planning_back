package com.salesmanagementplatform.orders.model.product;

import com.salesmanagementplatform.orders.model.customer.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductCategoryModel {

    private Long id;

    @NotBlank(message = "category cannot be blank or null")
    @Size(max = 60, min = 3, message = "'category' must be between 3 and 60 characters")
    private String category;

    @NotBlank(message = "description cannot be blank or null")
    @Size(max = 250, min = 10, message = "description must be between 10 and 250 characters")
    private String description;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    private Status status;
}
