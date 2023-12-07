package com.salesmanagementplatform.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
@Entity
@Table(name = "productStatus")
public class ProductStatusModel {

    @Null(message = "The id field must be null")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "status cannot be blank or null")
    @Column(name = "status", length = 50, nullable = false,unique = true)
    private String status;

    @NotBlank(message = "description cannot be blank or null")
    @Column(name = "description", length = 250, nullable = false)
    private String description;

}
