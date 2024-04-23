package com.erp.accesscontrol.service;

import com.erp.accesscontrol.dto.UpdatePasswordDTO;
import com.erp.accesscontrol.model.UserModel;

public interface UserService {

    UserModel getUser(String username);
    void saveUser(UserModel user);
    void updateUser(UserModel updateUser);
    void updatePassword(UpdatePasswordDTO updatePassword);
    String changePasswordFromSupport(String username);
    void deleteUser(String username);

}