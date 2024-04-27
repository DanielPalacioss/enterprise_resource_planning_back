package com.erp.accesscontrol.service;

import com.erp.accesscontrol.error.exceptions.RequestException;
import com.erp.accesscontrol.model.RoleModel;
import com.erp.accesscontrol.model.Status;
import com.erp.accesscontrol.repository.RoleRepository;
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
public class RoleServiceImp implements RoleService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;
    @Override
    public List<RoleModel> getAllRole(String status) {
        logger.info("Start search for all role");
        List<RoleModel> roleList = new ArrayList<RoleModel>();
        if(status.replaceAll(" ","").equalsIgnoreCase("active")) roleList= roleRepository.findAllByStatus_Id(true).orElseThrow(() -> new RequestException("La lista de roles en estado '"+status+"' está vacía","100-Continue"));
        else if (status.replaceAll(" ","").equalsIgnoreCase("inactive")) roleList= roleRepository.findAllByStatus_Id(false).orElseThrow(() -> new RequestException("La lista de roles en estado '"+status+"' está vacía","100-Continue"));
        else throw new RequestException("No existe el estado: '"+status+"'","100-Continue");
        roleList.forEach(RoleModel::convertPermissionsStringToList);
        return roleList;
    }

    @Override
    public void saveRole(RoleModel role) {
        if(statusRepository.count() < 1) throw new RequestException("No status created","404-Not Found");
        if(role.getId()==null)
        {
            role.setCreationDate(LocalDateTime.now());
            role.setUpdateDate(null);
            Status status = statusRepository.findById(true).orElseThrow(() -> new RequestException("Status not found with id true", "404-Not Found"));
            role.setStatus(status);
            role.convertPermissionsListToString();
            logger.info("Start the creation of role");
            roleRepository.save(role);
        }
        else throw new RequestException("The role id must be null","400-Bad Request");
    }

    @Override
    public void updateRole(RoleModel updateRole) {
        RoleModel role = roleRepository.findById(updateRole.getId()).orElseThrow(() -> new RequestException("RoleModel not found","404-Not Found"));
        role.setPermissionsList(updateRole.getPermissionsList());
        role.convertPermissionsListToString();
        role.setName(updateRole.getName());
        Status status = statusRepository.findById(true).orElseThrow(() -> new RequestException("Status not found with id true", "404-Not Found"));
        role.setStatus(status);
        role.setUpdateDate(LocalDateTime.now());
        logger.info("Start the update of role");
        roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        RoleModel role = roleRepository.findById(roleId).orElseThrow(() -> new RequestException("RoleModel not found","404-Not Found"));
        Status status = statusRepository.findById(false).orElseThrow(() -> new RequestException("Status not found with id false", "404-Not Found"));
        role.setStatus(status);
        logger.info("Start deleting role");
        roleRepository.save(role);
    }
}
