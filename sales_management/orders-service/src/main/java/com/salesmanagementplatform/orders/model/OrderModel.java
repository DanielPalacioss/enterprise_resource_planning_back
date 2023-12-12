package com.salesmanagementplatform.orders.model;

import com.salesmanagementplatform.orders.model.customer.CustomerModel;
import com.salesmanagementplatform.orders.model.product.ProductDetails;
import com.salesmanagementplatform.orders.model.product.ProductModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "order")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 200, message = "Shipping address must be between 3 and 200 characters")
    @Column(name = "shippingAddress", length = 200, nullable = false)
    private String shippingAddress;

    @NotBlank(message = "The method of payment cannot be blank or null")
    @Column(name = "methodOfPayment", length = 50, nullable = false)
    private String methodOfPayment;

    @Column(name="orderDate", nullable = false, updatable = false)
    private LocalDateTime orderDate;

    @NotNull(message = "Order details cannot be null")
    @Column(name = "orderDetails", nullable = false)
    private List<ProductDetails> orderDetails;

    @NotNull(message = "product list cannot be null")
    @Column(name = "products", nullable = false)
    private List<ProductModel> products;

    @DecimalMin(value = "1", message = "The minimum subTotal is 1")
    @NotNull(message = "The subTotal cannot be null")
    @Column(name = "subTotal", nullable = false)
    private Double subTotal;

    @DecimalMin(value = "1", message = "The minimum total is 1")
    @NotNull(message = "The total cannot be null")
    @Column(name = "total", nullable = false)
    private Double total;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private CustomerModel customer;

    @ManyToOne
    @JoinColumn(name = "orderStatus", nullable = false)
    private OrderStatusModel orderStatus;
}
