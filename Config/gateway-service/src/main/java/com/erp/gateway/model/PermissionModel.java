package com.erp.gateway.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

//Esta entity no debe ser modificada a menos que ya se haya modificado desde el servicio de accesscontrol
@Data
@Entity
@Table(name = "permission")
public class PermissionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    private Status status;
}