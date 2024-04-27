package com.erp.accesscontrol.model;

import com.erp.accesscontrol.dto.AnswerDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Entity
@Table(name = "userSecurityQuestions")
public class UserSecurityQuestionsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answers", columnDefinition = "TEXT", nullable = false)
    private String answers;

    @Transient
    private List<AnswerDTO> answersList;

    @Transient
    private JsonNode answersJson;

    @NotNull(message = "user cannot be null")
    @OneToOne
    @JoinColumn(name = "userErp", nullable = false, updatable = false, unique = true)
    private UserModel user;

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

    public <T> JsonNode convertClassListToJson(List<T> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.valueToTree(list);
    }
}
