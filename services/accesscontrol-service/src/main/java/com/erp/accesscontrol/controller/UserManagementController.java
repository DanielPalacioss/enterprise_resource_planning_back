package com.erp.accesscontrol.controller;

import com.erp.accesscontrol.error.DataValidation;
import com.erp.accesscontrol.model.UserSecurityAnswer;
import com.erp.accesscontrol.service.UserManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("ac/userManagement")
@CrossOrigin(origins = "http://localhost:8095")
public class UserManagementController {

    @Autowired
    UserManagementService userManagementService;
    DataValidation dataValidation = new DataValidation();

    @GetMapping("{userId}")
    ResponseEntity<List<UserSecurityAnswer>> getUserSecurityQuestions(@PathVariable Long userId) throws IOException {
        return ResponseEntity.ok(userManagementService.getUserSecurityAnswer(userId));
    }

    @PostMapping
    ResponseEntity<?> saveUserSecurityQuestions(@Valid @RequestBody List<UserSecurityAnswer> userSecurityAnswers, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        userManagementService.saveUserSecurityQuestions(userSecurityAnswers);
        return ResponseEntity.status(HttpStatus.CREATED).body("The user security questions has been created successfully.");
    }

    @PutMapping
    ResponseEntity<?> updateUserSecurityQuestions(@Valid @RequestBody List<UserSecurityAnswer> updateUserSecurityAnswers, Long userId, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        userManagementService.updateUserSecurityQuestions(updateUserSecurityAnswers, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body("The user security questions has been successfully modified.");
    }

    @DeleteMapping
    ResponseEntity<?> deleteUserSecurityQuestions(@RequestParam Long userSecurityAnswerId, @RequestParam Long userId)
    {
        userManagementService.deleteUserSecurityQuestion(userSecurityAnswerId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body("The user security questions has been successfully deleted.");
    }
}
