package com.salesmanagementplatform.equity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "type")
public class AssetTypeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "asset type cannot be blank or null")
    @Column(name = "type", length = 50, nullable = false, unique = true)
    private String assetType;

    @NotBlank(message = "description cannot be blank or null")
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name="creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name="updateDate")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "statusId", nullable = false)
    private Status status;
}
