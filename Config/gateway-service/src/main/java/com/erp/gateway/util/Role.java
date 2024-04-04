package com.erp.gateway.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
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

    @Column(name = "permissionsList", columnDefinition = "TEXT", nullable = false)
    private String permissionsList;

    @Transient
    private JsonNode permissionsJson;

    @Transient
    private List<Permission> permissions;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    public JsonNode convertStringToJsonNode(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readTree(jsonString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> jsonNodeToList(JsonNode jsonNode, Class<T> valueType) throws IOException {
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
}
