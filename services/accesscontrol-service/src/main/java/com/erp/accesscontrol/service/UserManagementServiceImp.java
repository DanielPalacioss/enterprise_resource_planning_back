package com.erp.accesscontrol.service;

import com.erp.accesscontrol.error.exceptions.RequestException;
import com.erp.accesscontrol.model.UserSecurityAnswer;
import com.erp.accesscontrol.repository.SecurityQuestionsRepository;
import com.erp.accesscontrol.repository.UserRepository;
import com.erp.accesscontrol.repository.UserSecurityAnswerRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserManagementServiceImp implements UserManagementService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    private final UserSecurityAnswerRepository userSecurityAnswerRepository;
    private final SecurityQuestionsRepository securityQuestionsRepository;

    @Override
    public List<UserSecurityAnswer> getUserSecurityAnswer(Long userId) throws IOException {
        List<UserSecurityAnswer> userSecurityQuestions = userSecurityAnswerRepository.findAllByUser_Id(userId).orElseThrow(()-> new RequestException("User security questions and answer not found","404-Not Found"));
        logger.info("starting search by user security questions id");
        return userSecurityQuestions;
    }

    @Override
    public void saveUserSecurityQuestions(List<UserSecurityAnswer> userSecurityAnswers) {
        if(securityQuestionsRepository.count() < 1) throw new RequestException("No security questions created","404-Not Found");
        userSecurityAnswers = userSecurityAnswers.stream().peek(userSecurityAnswer ->
        {
            if(userSecurityAnswer.getId() == null)
            {
                userSecurityAnswer.setUpdateDate(null);
                userSecurityAnswer.setCreationDate(LocalDateTime.now());
            } else throw new RequestException("The user security questions id must be null","400-Bad Request");
        }).collect(Collectors.toList());
        logger.info("Start the creation of user security questions");
        userSecurityAnswerRepository.saveAll(userSecurityAnswers);
    }

    @Override
    public void updateUserSecurityQuestions(List<UserSecurityAnswer> updateUserSecurityAnswers, Long userId) {
        List<UserSecurityAnswer> userSecurityAnswers = userSecurityAnswerRepository.findAllByUser_Id(userId).orElseThrow(()-> new RequestException("User security questions and answer not found","404-Not Found"));
        userSecurityAnswers = userSecurityAnswers.get(0).updateAllUserSecurityAnswer(userSecurityAnswers, updateUserSecurityAnswers);
        logger.info("Start the update of user security questions");
        userSecurityAnswerRepository.saveAll(userSecurityAnswers);
    }

    @Override
    public void deleteUserSecurityQuestion(Long userSecurityAnswerId, Long userId) {
        if (userSecurityAnswerRepository.countAllByUser_Id(userId) > 1) userSecurityAnswerRepository.deleteById(userSecurityAnswerId);
        else throw new RequestException("You only have 1 security response recorded.","400-Bad Request");
    }
}
