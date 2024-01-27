package com.salesmanagementplatform.equity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "assetStatus")
public class AssetStatusModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "asset Status cannot be blank or null")
    @Column(name = "assetStatus", length = 50, nullable = false, unique = true)
    private String assetStatus;

    @NotBlank(message = "description cannot be blank or null")
    @Column(name = "description", nullable = false)
    private String description;

}
