package com.erp.accesscontrol.service;

import com.erp.accesscontrol.model.UpdateEmail;
import com.erp.accesscontrol.model.UpdatePassword;
import com.erp.accesscontrol.model.UserModel;

public interface UserService {

    void saveUser(UserModel user);
    void updateUser(UserModel updateUser, String token);
    void updateEmail(UpdateEmail updateEmail);
    void updatePassword(UpdatePassword updatePassword);
    void changePasswordFromSupport(String username);
    void deleteUser(String username);

}