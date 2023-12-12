package com.salesmanagementplatform.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "productStock")
public class ProductStockModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "quantity cannot be null")
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @OneToOne
    @JoinColumn(name = "product", nullable = false, updatable = false)
    private ProductModel product;

    public String updateStatusProduct()
    {
        if(getQuantity()==0) return "outofstock";
        else if (getQuantity() >0) return "available";
        else return "";
    }
}
