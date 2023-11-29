package com.salesmanagementplatform.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "productStock")
public class ProductStockModel {

    @Null(message = "The id field must be null")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "quantity cannot be null")
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @OneToOne
    @JoinColumn(name = "productId", nullable = false)
    private ProductModel productModel;
}
