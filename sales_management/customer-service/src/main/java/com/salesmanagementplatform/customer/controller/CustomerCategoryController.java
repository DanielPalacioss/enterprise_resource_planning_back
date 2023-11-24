package com.salesmanagementplatform.customer.controller;

import com.salesmanagementplatform.customer.model.CustomerCategoryModel;
import com.salesmanagementplatform.customer.service.CustomerCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/customerCategory")
@CrossOrigin(origins = "http://localhost:8081")
public class CustomerCategoryController {

    @Autowired
    CustomerCategoryService customerCategoryService;

    @GetMapping
    public ResponseEntity<List<CustomerCategoryModel>> getAllCustomersCategory() {
        return ResponseEntity.ok(customerCategoryService.listOfAllCustomersCategory());
    }

    @PostMapping
    public ResponseEntity<?> saveCustomerCategory(@Valid @RequestBody CustomerCategoryModel customerCategoryModel, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        else {
        customerCategoryService.saveCustomerCategory(customerCategoryModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The customer category has been created successfully.");
        }
    }

    @DeleteMapping("/{customerCategoryId}")
    public ResponseEntity<?> deleteCustomerCategory(@PathVariable Long customerCategoryId)
    {
        customerCategoryService.deleteCustomerCategory(customerCategoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body("The customer category has been successfully deleted.");
    }

    @PutMapping()
    public ResponseEntity<?> updateCustomerCategory(@Valid @RequestBody CustomerCategoryModel customerCategoryModel, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        else {
            customerCategoryService.updateCustomerCategory(customerCategoryModel);
            return ResponseEntity.status(HttpStatus.CREATED).body("The customer category has been successfully modified.");
        }
    }
}
