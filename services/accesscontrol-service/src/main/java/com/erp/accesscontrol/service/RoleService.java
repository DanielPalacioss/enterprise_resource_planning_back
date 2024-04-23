package com.erp.accesscontrol.service;

import com.erp.accesscontrol.model.RoleModel;

import java.util.List;

public interface RoleService {

    List<RoleModel> getAllRole(String status);
    void saveRole(RoleModel role);
    void updateRole(RoleModel updateRole);
    void deleteRole(Long roleId);
}
