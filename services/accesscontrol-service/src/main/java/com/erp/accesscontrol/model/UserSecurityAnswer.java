package com.erp.accesscontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @NotBlank(message = "answer cannot be blank or null")
    @Column(name = "answer", nullable = false)
    private String answer;

    @JsonIgnore
    @NotNull(message = "user cannot be null")
    @ManyToOne(targetEntity = UserModel.class)
    @JoinColumn(nullable = false, updatable = false)
    private UserModel user;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    public List<UserSecurityAnswer> updateAllUserSecurityAnswer(List<UserSecurityAnswer> userSecurityAnswers, List<UserSecurityAnswer> updateUserSecurityAnswers)
    {
        List<UserSecurityAnswer> userSecurityAnswerList = new ArrayList<>();
        for (int i = 0; i < userSecurityAnswers.size(); i++) {
            if(userSecurityAnswers.get(i).id.equals(updateUserSecurityAnswers.get(i).id))
            {
                userSecurityAnswers.get(i).setUpdateDate(LocalDateTime.now());
                userSecurityAnswers.get(i).setAnswer(updateUserSecurityAnswers.get(i).getAnswer());
                userSecurityAnswerList.add(userSecurityAnswers.get(i));
            }
        }
        return userSecurityAnswers;
    }
}
