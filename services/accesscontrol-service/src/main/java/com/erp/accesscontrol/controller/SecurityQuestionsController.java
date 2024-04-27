package com.erp.accesscontrol.controller;

import com.erp.accesscontrol.error.DataValidation;
import com.erp.accesscontrol.model.SecurityQuestionsModel;
import com.erp.accesscontrol.service.SecurityQuestionsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ac/securityQuestions")
@CrossOrigin(origins = "http://localhost:8095")
public class SecurityQuestionsController {

    @Autowired
    SecurityQuestionsService securityQuestionsService;
    DataValidation dataValidation = new DataValidation();

    @GetMapping
    ResponseEntity<List<SecurityQuestionsModel>> getAllSecurityQuestions(@RequestParam String status)
    {
        return ResponseEntity.ok(securityQuestionsService.getAllSecurityQuestions(status));
    }

    @PutMapping
    ResponseEntity<?> updateSecurityQuestions(@Valid @RequestBody SecurityQuestionsModel updateSecurityQuestions, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        securityQuestionsService.updateSecurityQuestions(updateSecurityQuestions);
        return ResponseEntity.status(HttpStatus.CREATED).body("The security questions has been successfully modified.");
    }

    @PostMapping
    ResponseEntity<?> saveSecurityQuestions(@Valid @RequestBody SecurityQuestionsModel securityQuestions, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        securityQuestionsService.saveSecurityQuestions(securityQuestions);
        return ResponseEntity.status(HttpStatus.CREATED).body("The security questions has been successfully modified.");
    }

    @DeleteMapping("{securityQuestionsId}")
    ResponseEntity<?> deleteSecurityQuestions(@PathVariable Long securityQuestionsId)
    {
        securityQuestionsService.deleteSecurityQuestions(securityQuestionsId);
        return ResponseEntity.status(HttpStatus.CREATED).body("The security questions has been successfully deleted.");
    }
}
