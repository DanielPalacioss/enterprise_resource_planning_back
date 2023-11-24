package com.salesmanagementplatform.customer.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customerReference")
public class CustomerReferenceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "reference", length = 60, nullable = false)
    private String reference;

    @Column(name = "description", length = 250, nullable = false)
    private String description;

    @Column(name = "creationDate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "statusId", nullable = false)
    private Status status;
}
