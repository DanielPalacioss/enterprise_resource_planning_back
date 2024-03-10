package com.erp.gateway.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name cannot be blank or null")
    private String name;

    @Column(name = "permissionsList",columnDefinition = "TEXT", nullable = false)
    private String permissionsList;

    @Transient
    private List<Permission> permissions;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    public void convertStringToList() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        setPermissions(mapper.readValue(permissionsList, List.class));
    }
    public void convertListToString() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        setPermissionsList(mapper.writeValueAsString(permissions));
    }
}
