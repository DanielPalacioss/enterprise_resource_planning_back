package com.erp.accesscontrol.controller;

import com.erp.accesscontrol.dto.UpdatePasswordDTO;
import com.erp.accesscontrol.error.DataValidation;
import com.erp.accesscontrol.error.exceptions.RequestException;
import com.erp.accesscontrol.model.UserModel;
import com.erp.accesscontrol.repository.UserRepository;
import com.erp.accesscontrol.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("ac/user")
@CrossOrigin(origins = "http://localhost:8095")
public class UserController {

    @Autowired
    UserService userService;
    DataValidation dataValidation = new DataValidation();

    @GetMapping
    ResponseEntity<UserModel> getUser(@RequestParam String username)
    {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @PostMapping
    ResponseEntity<?> saveUser(@Valid @RequestBody UserModel user, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("The user has been created successfully.");
    }

    @PutMapping("updateUser")
    ResponseEntity<?> updateUser(@Valid @RequestBody UserModel updateUser, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        userService.updateUser(updateUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("The user has been successfully modified.");

    }

    @PutMapping("updatePassword")
    ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordDTO updatePassword, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        userService.updatePassword(updatePassword);
        return ResponseEntity.status(HttpStatus.CREATED).body("The user password has been successfully modified.");
    }

    @PutMapping("changePasswordFromSupport")
    ResponseEntity<String> changePasswordFromSupport(@RequestParam String username)
    {
        return ResponseEntity.ok(userService.changePasswordFromSupport(username));
    }

    @DeleteMapping
    ResponseEntity<?> deleteUser(@RequestParam String username)
    {
        userService.deleteUser(username);
        return ResponseEntity.status(HttpStatus.CREATED).body("The customer has been successfully deleted.");
    }
}
