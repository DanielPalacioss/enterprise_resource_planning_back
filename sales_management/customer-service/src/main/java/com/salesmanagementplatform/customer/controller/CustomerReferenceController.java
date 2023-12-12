package com.salesmanagementplatform.customer.controller;

import com.salesmanagementplatform.customer.error.DataValidation;
import com.salesmanagementplatform.customer.model.CustomerReferenceModel;
import com.salesmanagementplatform.customer.service.CustomerReferenceService;
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
@RequestMapping("api/customerReference")
@CrossOrigin(origins = "http://localhost:8081")
public class CustomerReferenceController {

    @Autowired
    CustomerReferenceService customerReferenceService;

    DataValidation dataValidation = new DataValidation();
    @GetMapping("/{status}")
    public ResponseEntity<List<CustomerReferenceModel>> getAllCustomersReference(@PathVariable String status) {
        return ResponseEntity.ok(customerReferenceService.listOfAllCustomersReference(status.replaceAll(" ","").toLowerCase()));
    }

    @PostMapping
    public ResponseEntity<?> saveCustomerReference(@Valid @RequestBody CustomerReferenceModel customerReferenceModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        customerReferenceService.saveCustomerReference(customerReferenceModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The customer Reference has been created successfully.");
    }

    @DeleteMapping("/{customerReferenceId}")
    public ResponseEntity<?> deleteCustomerReference(@PathVariable Long customerReferenceId)
    {
        customerReferenceService.deleteCustomerReference(customerReferenceId);
        return ResponseEntity.status(HttpStatus.CREATED).body("The customer Reference has been successfully deleted.");
    }

    @PutMapping()
    public ResponseEntity<?> updateCustomerReference(@Valid @RequestBody CustomerReferenceModel customerReferenceModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        customerReferenceService.updateCustomerReference(customerReferenceModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The customer Reference has been successfully modified.");

    }
}
