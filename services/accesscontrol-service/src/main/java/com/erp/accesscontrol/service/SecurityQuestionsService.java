package com.erp.accesscontrol.service;

import com.erp.accesscontrol.model.SecurityQuestionsModel;

import java.util.List;

public interface SecurityQuestionsService {

    List<SecurityQuestionsModel> getAllSecurityQuestions(String status);

    void updateSecurityQuestions(SecurityQuestionsModel updateSecurityQuestions);

    void saveSecurityQuestions(SecurityQuestionsModel securityQuestions);

    void deleteSecurityQuestions(Long securityQuestionsId);
}
