package com.erp.salesmanagement.model.order;

import com.erp.salesmanagement.model.product.ProductModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Table(name = "orderDetails")
@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product", nullable = false)
    private ProductModel product;

    @Min(value = 1, message = "The minimum units is 1")
    @NotNull(message = "units cannot be null")
    @Column(name = "units", nullable = false)
    private int units;

    @DecimalMin(value = "1", message = "The minimum subTotal is 1")
    @Column(name = "subtotal")
    private Double subtotal;

    @DecimalMin(value = "1", message = "The minimum total is 1")
    @Column(name = "total")
    private Double total;

    @JsonIgnore
    @ManyToOne(targetEntity = OrderModel.class)
    @JoinColumn(updatable = false)
    private OrderModel orderS;
}
