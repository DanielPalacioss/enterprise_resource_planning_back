package com.erp.gateway.service;

import com.erp.gateway.model.UpdateEmail;
import com.erp.gateway.model.UpdatePassword;
import com.erp.gateway.model.UserModel;

public interface UserService {

    void saveUser(UserModel user);
    void updateUser(UserModel updateUser, String token);
    void updateEmail(UpdateEmail updateEmail);
    void updatePassword(UpdatePassword updatePassword);
    void changePasswordFromSupport(String username);
    void deleteUser(String username);

}