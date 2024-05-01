package com.erp.accesscontrol.service;

import com.erp.accesscontrol.model.UserSecurityAnswer;

import java.io.IOException;
import java.util.List;

public interface UserManagementService {

    List<UserSecurityAnswer> getUserSecurityAnswer(Long userId) throws IOException;
    void saveUserSecurityQuestions(List<UserSecurityAnswer> userSecurityAnswer);
    void updateUserSecurityQuestions(List<UserSecurityAnswer> updateUserSecurityAnswers, Long userId);
    void deleteUserSecurityQuestion(Long userSecurityAnswerId, Long userId);
}
