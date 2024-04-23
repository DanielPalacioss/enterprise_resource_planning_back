package com.erp.accesscontrol.controller;

import com.erp.accesscontrol.error.DataValidation;
import com.erp.accesscontrol.service.PermissionService;
import com.erp.accesscontrol.model.PermissionModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ac/permission")
@CrossOrigin(origins = "http://localhost:8095")
public class PermissionController {

    @Autowired
    PermissionService permissionService;
    DataValidation dataValidation = new DataValidation();

    @GetMapping
    ResponseEntity<List<PermissionModel>> getAllPermission(@RequestParam String status)
    {
        return ResponseEntity.ok(permissionService.getAllPermission(status));
    }

    @PutMapping
    ResponseEntity<?> updatePermission(@Valid @RequestBody PermissionModel updatePermission, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        permissionService.updatePermission(updatePermission);
        return ResponseEntity.status(HttpStatus.CREATED).body("The permission has been successfully modified.");
    }

    @PostMapping
    ResponseEntity<?> savePermission(@Valid @RequestBody PermissionModel permission, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        permissionService.savePermission(permission);
        return ResponseEntity.status(HttpStatus.CREATED).body("The permission has been created successfully.");
    }

    @DeleteMapping("{permissionId}")
    ResponseEntity<?> deletePermission(@PathVariable Long permissionId)
    {
        permissionService.deletePermission(permissionId);
        return ResponseEntity.status(HttpStatus.CREATED).body("The permission has been successfully deleted.");
    }
}
