package com.erp.accesscontrol.service;

import com.erp.accesscontrol.model.UserSecurityQuestionsModel;

import java.io.IOException;

public interface UserManagementService {

    UserSecurityQuestionsModel getUserSecurityQuestions(Long userId) throws IOException;
    void saveUserSecurityQuestions(UserSecurityQuestionsModel userSecurityQuestions);
    void updateUserSecurityQuestions(UserSecurityQuestionsModel updateUserSecurityQuestions);
}
