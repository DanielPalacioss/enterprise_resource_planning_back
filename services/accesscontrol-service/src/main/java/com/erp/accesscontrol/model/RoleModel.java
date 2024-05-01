package com.erp.accesscontrol.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Entity
@Table(name = "role")
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name cannot be blank or null")
    @Size(min = 3, max = 60, message = "name must be between 3 and 60 characters")
    @Column(name = "name", unique = true)
    private String name;

    @NotNull(message = "permissions cannot be null")
    @ManyToMany(targetEntity = PermissionModel.class)
    @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role"), inverseJoinColumns = @JoinColumn(name = "permission"))
    private List<PermissionModel> permissions;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    private Status status;
}
