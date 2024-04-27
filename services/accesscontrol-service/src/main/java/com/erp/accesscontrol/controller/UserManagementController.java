package com.erp.accesscontrol.controller;

import com.erp.accesscontrol.error.DataValidation;
import com.erp.accesscontrol.model.UserSecurityQuestionsModel;
import com.erp.accesscontrol.service.UserManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("ac/userManagement")
@CrossOrigin(origins = "http://localhost:8095")
public class UserManagementController {

    @Autowired
    UserManagementService userManagementService;
    DataValidation dataValidation = new DataValidation();

    @GetMapping("{userId}")
    ResponseEntity<UserSecurityQuestionsModel> getUserSecurityQuestions(@PathVariable Long userId) throws IOException {
        return ResponseEntity.ok(userManagementService.getUserSecurityQuestions(userId));
    }

    @PostMapping
    ResponseEntity<?> saveUserSecurityQuestions(@Valid @RequestBody UserSecurityQuestionsModel userSecurityQuestions, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        userManagementService.saveUserSecurityQuestions(userSecurityQuestions);
        return ResponseEntity.status(HttpStatus.CREATED).body("The user security questions has been created successfully.");
    }

    @PutMapping
    ResponseEntity<?> updateUserSecurityQuestions(@Valid @RequestBody UserSecurityQuestionsModel updateUserSecurityQuestions, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        userManagementService.updateUserSecurityQuestions(updateUserSecurityQuestions);
        return ResponseEntity.status(HttpStatus.CREATED).body("The user security questions has been successfully modified.");
    }
}
