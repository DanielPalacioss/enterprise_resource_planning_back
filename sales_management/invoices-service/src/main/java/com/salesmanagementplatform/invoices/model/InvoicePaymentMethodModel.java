package com.salesmanagementplatform.invoices.model;

import com.salesmanagementplatform.invoices.model.customer.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "InvoicePaymentMethod")
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

    @Column(name="creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name="updateDate")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "statusId", nullable = false)
    private Status status;
}
