package com.erp.accesscontrol.controller;

import com.erp.accesscontrol.error.DataValidation;
import com.erp.accesscontrol.model.RoleModel;
import com.erp.accesscontrol.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ac/role")
@CrossOrigin(origins = "http://localhost:8095")
public class RoleController {

    @Autowired
    private RoleService roleService;
    DataValidation dataValidation = new DataValidation();

    @GetMapping
    ResponseEntity<List<RoleModel>> getAllRole(@RequestParam String status) {
        return ResponseEntity.ok(roleService.getAllRole(status));
    }

    @PostMapping
    ResponseEntity<?> saveRole(@Valid @RequestBody RoleModel role, BindingResult bindingResult) {
        dataValidation.handleValidationError(bindingResult);
        roleService.saveRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body("The user has been created successfully.");
    }

    @PutMapping
    ResponseEntity<?> updateRole(@Valid @RequestBody RoleModel updateRole, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        roleService.updateRole(updateRole);
        return ResponseEntity.status(HttpStatus.CREATED).body("The user has been successfully modified.");
    }

    @DeleteMapping("{roleId}")
    ResponseEntity<?> deleteRole(@PathVariable Long roleId)
    {
        roleService.deleteRole(roleId);
        return ResponseEntity.status(HttpStatus.CREATED).body("The customer has been successfully deleted.");
    }
}
