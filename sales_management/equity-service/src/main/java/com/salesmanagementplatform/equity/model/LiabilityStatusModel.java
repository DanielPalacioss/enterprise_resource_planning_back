package com.salesmanagementplatform.equity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "liabilityStatus")
public class LiabilityStatusModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "liability Status cannot be blank or null")
    @Column(name = "liabilityStatus", length = 50, nullable = false, unique = true)
    private String liabilityStatus;

    @NotBlank(message = "description cannot be blank or null")
    @Column(name = "description", nullable = false)
    private String description;

}
