package com.salesmanagementplatform.equity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "liability")
public class LiabilityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The reference cannot be blank or null")
    @Column(name= "reference", length = 50, nullable = false)
    private String reference;

    @NotBlank(message = "The description cannot be blank or null")
    @Column(name= "description", nullable = false, length = 5000)
    private String description;

    @Future(message = "The date must be in the future.")
    @Column(name="expirationDate")
    private LocalDate expirationDate;

    @DecimalMin(value = "1", message = "The minimum value is 1")
    @Column(name = "value", nullable = false)
    private double value;

    @Column(name="creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name="updateDate")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "liabilityType")
    private LiabilityTypeModel liabilityType;

    @ManyToOne
    @JoinColumn(name = "liabilityStatus", nullable = false)
    private LiabilityStatusModel liabilityStatus;
}