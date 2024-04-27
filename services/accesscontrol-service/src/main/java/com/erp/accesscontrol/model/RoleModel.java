package com.erp.accesscontrol.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @Column(name = "permissions", columnDefinition = "TEXT", nullable = false)
    private String permissions;

    @Transient
    private JsonNode permissionsJson;

    @Transient
    private List<PermissionModel> permissionsList;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    private Status status;

    public void convertPermissionsStringToList()
    {
        setPermissionsJson(convertStringToJsonNode(getPermissions()));
        try {
            setPermissionsList(jsonNodeToList(getPermissionsJson(), PermissionModel.class));
            setPermissions(null);
            setPermissionsJson(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void convertPermissionsListToString()
    {
        setPermissions(convertClassListToJson(getPermissionsList()).toString());
        setPermissionsList(null);
        setPermissionsJson(null);
    }
    private JsonNode convertStringToJsonNode(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readTree(jsonString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> List<T> jsonNodeToList(JsonNode jsonNode, Class<T> valueType) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        if (jsonNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonNode;
            Iterator<JsonNode> elements = arrayNode.elements();
            List<T> resultList = new ArrayList<>();
            while (elements.hasNext()) {
                JsonNode element = elements.next();
                T item = objectMapper.treeToValue(element, valueType);
                resultList.add(item);
            }
            return resultList;
        }
        return null;
    }

    private <T> JsonNode convertClassListToJson(List<T> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.valueToTree(list);
    }
}
