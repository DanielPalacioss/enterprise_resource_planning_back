package com.erp.purchasingmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "suppliersTelephone")
public class SupplierTelephone {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prefix", nullable = false)
    private InternationalTelephonePrefixes prefix;

    @NotNull(message = "Telephone cannot be null")
    @Min(value = 6, message = "the telephone must be greater than or equal to 6")
    @Column(name = "telephone", nullable = false, unique = true)
    private Long telephone;

    @ManyToOne
    @JoinColumn(name = "supplier")
    private SupplierModel supplier;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;
}
