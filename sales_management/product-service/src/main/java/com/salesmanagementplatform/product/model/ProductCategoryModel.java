package com.salesmanagementplatform.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "productCategory")
public class ProductCategoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "category cannot be blank or null")
    @Size(max = 60, min = 3, message = "'category' must be between 3 and 60 characters")
    @Column(name= "category", length = 60, nullable = false, unique = true)
    private String category;

    @NotBlank(message = "description cannot be blank or null")
    @Size(max = 250, min = 10, message = "description must be between 10 and 250 characters")
    @Column(name = "description", length = 250, nullable = false)
    private String description;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    private Status status;
}
