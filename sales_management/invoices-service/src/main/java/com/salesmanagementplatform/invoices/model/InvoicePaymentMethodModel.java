package com.salesmanagementplatform.invoices.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "InvoicePaymentMethodModel")
public class InvoicePaymentMethodModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "payment method cannot be blank or null")
    @Column(name = "paymentMethod", length = 50, nullable = false,unique = true)
    private String paymentMethod;

    @NotBlank(message = "description cannot be blank or null")
    @Column(name = "description", length = 250, nullable = false)
    private String description;

    @Column(name="paymentMethodDate", nullable = false, updatable = false)
    private LocalDateTime paymentMethodDate;

    @Column(name="paymentMethodDate")
    private LocalDateTime paymentMethodUpdateDate;
}
