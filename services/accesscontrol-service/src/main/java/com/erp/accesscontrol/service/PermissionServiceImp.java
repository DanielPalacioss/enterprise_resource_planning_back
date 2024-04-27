package com.erp.accesscontrol.service;

import com.erp.accesscontrol.error.exceptions.RequestException;
import com.erp.accesscontrol.model.PermissionModel;
import com.erp.accesscontrol.model.Status;
import com.erp.accesscontrol.repository.PermissionRepository;
import com.erp.accesscontrol.repository.StatusRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PermissionServiceImp implements PermissionService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    private final PermissionRepository permissionRepository;
    private final StatusRepository statusRepository;
    @Override
    public List<PermissionModel> getAllPermission(String status) {
        logger.info("Start search for all permission");
        List<PermissionModel> permissions = new ArrayList<PermissionModel>();
        if(status.replaceAll(" ","").equalsIgnoreCase("active")) permissions= permissionRepository.findAllByStatus_Id(true).orElseThrow(() -> new RequestException("La lista de permission en estado '"+status+"' está vacía","100-Continue"));
        else if (status.replaceAll(" ","").equalsIgnoreCase("inactive")) permissions= permissionRepository.findAllByStatus_Id(false).orElseThrow(() -> new RequestException("La lista de permission en estado '"+status+"' está vacía","100-Continue"));
        else throw new RequestException("No existe el estado: '"+status+"'","100-Continue");
        return permissions;
    }

    @Override
    public void updatePermission(PermissionModel updatePermission) {
        PermissionModel permission = permissionRepository.findById(updatePermission.getId()).orElseThrow(() -> new RequestException("PermissionModel not found","404-Not Found"));
        permission.setName(updatePermission.getName());
        permission.setUpdateDate(LocalDateTime.now());
        logger.info("Start the update of permissions");
        permissionRepository.save(permission);
    }

    @Override
    public void savePermission(PermissionModel permission) {
        if(statusRepository.count() < 1) throw new RequestException("No status created","404-Not Found");
        if(permission.getId()==null)
        {
            permission.setCreationDate(LocalDateTime.now());
            permission.setUpdateDate(null);
            Status status = statusRepository.findById(true).orElseThrow(() -> new RequestException("Status not found with id true", "404-Not Found"));
            permission.setStatus(status);
            logger.info("Start the creation of permission");
            permissionRepository.save(permission);
        }
        else throw new RequestException("The permission id must be null","400-Bad Request");
    }

    @Override
    public void deletePermission(Long permissionId) {
        PermissionModel permission = permissionRepository.findById(permissionId).orElseThrow(() -> new RequestException("PermissionModel not found","404-Not Found"));
        Status status = statusRepository.findById(false).orElseThrow(() -> new RequestException("Status not found with id false", "404-Not Found"));
        permission.setStatus(status);
        logger.info("Start deleting permission");
        permissionRepository.save(permission);
    }
}
