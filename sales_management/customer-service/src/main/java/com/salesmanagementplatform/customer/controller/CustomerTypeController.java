package com.salesmanagementplatform.customer.controller;

import com.salesmanagementplatform.customer.model.CustomerTypeModel;
import com.salesmanagementplatform.customer.service.CustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/customerType")
@CrossOrigin(origins = "http://localhost:8081")
public class CustomerTypeController {

    @Autowired
    CustomerTypeService customerTypeService;

    @GetMapping
    public ResponseEntity<List<CustomerTypeModel>> getAllCustomersType() {
        return ResponseEntity.ok(customerTypeService.listOfAllCustomersType());
    }

    @PostMapping
    public ResponseEntity<?> saveCustomerType(@RequestBody CustomerTypeModel customerTypeModel, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        else {
            customerTypeService.saveCustomerType(customerTypeModel);
            return ResponseEntity.status(HttpStatus.CREATED).body("The customer type has been created successfully.");
        }
    }

    @DeleteMapping("/{customerTypeId}")
    public ResponseEntity<?> deleteCustomerType(@PathVariable Long customerTypeId)
    {
        customerTypeService.deleteCustomerType(customerTypeId);
        return ResponseEntity.status(HttpStatus.CREATED).body("The customer type has been successfully deleted.");
    }

    @PutMapping
    public ResponseEntity<?> updateCustomerReference(@RequestBody CustomerTypeModel customerTypeModel, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        else {
            customerTypeService.updateCustomerType(customerTypeModel);
            return ResponseEntity.status(HttpStatus.CREATED).body("The customer Reference has been successfully modified.");
        }
    }
}
