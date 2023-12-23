package com.salesmanagementplatform.invoices.model.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductStatusModel {

    private Long id;

    @NotBlank(message = "status cannot be blank or null")
    private String status;

    @NotBlank(message = "description cannot be blank or null")
    private String description;

}
