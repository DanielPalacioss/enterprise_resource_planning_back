package com.erp.accesscontrol.service;

import com.erp.accesscontrol.model.PermissionModel;

import java.util.List;

public interface PermissionService {

    List<PermissionModel> getAllPermission(String status);

    void updatePermission(PermissionModel updatePermission);

    void savePermission(PermissionModel permission);

    void deletePermission(Long permissionId);
}
