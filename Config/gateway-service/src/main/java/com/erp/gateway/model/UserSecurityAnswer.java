package com.erp.gateway.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_security_answer")
public class UserSecurityAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = SecurityQuestionsModel.class)
    @JoinColumn(name = "security_questions", nullable = false, updatable = false)
    private SecurityQuestionsModel securityQuestions;

    @Column(name = "answer", nullable = false)
    private String answer;

    @ManyToOne(targetEntity = UserModel.class)
    @JoinColumn(nullable = false, updatable = false)
    private UserModel user;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;
}
