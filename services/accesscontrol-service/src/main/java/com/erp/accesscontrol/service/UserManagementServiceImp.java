package com.erp.accesscontrol.service;

import com.erp.accesscontrol.dto.AnswerDTO;
import com.erp.accesscontrol.error.exceptions.RequestException;
import com.erp.accesscontrol.model.UserSecurityQuestionsModel;
import com.erp.accesscontrol.repository.SecurityQuestionsRepository;
import com.erp.accesscontrol.repository.UserRepository;
import com.erp.accesscontrol.repository.UserSecurityQuestionsRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserManagementServiceImp implements UserManagementService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    private final UserSecurityQuestionsRepository userSecurityQuestionsRepository;
    private final SecurityQuestionsRepository securityQuestionsRepository;
    private final UserRepository userRepository;

    @Override
    public UserSecurityQuestionsModel getUserSecurityQuestions(Long userId) throws IOException {
        UserSecurityQuestionsModel userSecurityQuestions = userSecurityQuestionsRepository.findByUser_Id(userId).orElseThrow(()-> new RequestException("User security questions not found","404-Not Found"));
        userSecurityQuestions.setAnswersList(userSecurityQuestions.jsonNodeToList(userSecurityQuestions.convertStringToJsonNode(userSecurityQuestions.getAnswers()), AnswerDTO.class));
        userSecurityQuestions.setAnswers(null);
        logger.info("starting search by user security questions id");
        return userSecurityQuestions;
    }

    @Override
    public void saveUserSecurityQuestions(UserSecurityQuestionsModel userSecurityQuestions) {
        if(securityQuestionsRepository.count() < 1) throw new RequestException("No security questions created","404-Not Found");
        if(userSecurityQuestions.getId() == null)
        {
            userRepository.findById(userSecurityQuestions.getUser().getId()).orElseThrow(() -> new RequestException("User not found","404-Not found"));
            userSecurityQuestions.setAnswers(userSecurityQuestions.convertClassListToJson(userSecurityQuestions.getAnswersList()).toString());
            userSecurityQuestions.setCreationDate(LocalDateTime.now());
            userSecurityQuestionsRepository.save(userSecurityQuestions);
            logger.info("Start the creation of user security questions");
        } else throw new RequestException("The user security questions id must be null","400-Bad Request");
    }

    @Override
    public void updateUserSecurityQuestions(UserSecurityQuestionsModel updateUserSecurityQuestions) {
        UserSecurityQuestionsModel userSecurityQuestions = userSecurityQuestionsRepository.findById(updateUserSecurityQuestions.getId()).orElseThrow(()-> new RequestException("User security questions not found","404-Not found"));
        userRepository.findById(updateUserSecurityQuestions.getUser().getId()).orElseThrow(() -> new RequestException("User not found","404-Not found"));
        userSecurityQuestions.setAnswers(updateUserSecurityQuestions.convertClassListToJson(updateUserSecurityQuestions.getAnswersList()).toString());
        userSecurityQuestions.setUpdateDate(LocalDateTime.now());
        logger.info("Start the update of user security questions");
        userSecurityQuestionsRepository.save(userSecurityQuestions);
    }
}
