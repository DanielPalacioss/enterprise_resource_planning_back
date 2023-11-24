package com.salesmanagementplatform.customer.controller;

import com.salesmanagementplatform.customer.model.CustomerModel;
import com.salesmanagementplatform.customer.service.CustomerService;
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
@RequestMapping("api/customer")
@CrossOrigin(origins = "http://localhost:8081")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerModel>> getAllCustomers() {
        return (customerService.listOfAllCustomers().isEmpty()) ? ResponseEntity.noContent().header("No hay clientes", "La lista de clientes está vacía").build() : ResponseEntity.ok(customerService.listOfAllCustomers());
    }

    @GetMapping("{customerId}")
    public ResponseEntity<CustomerModel> getCustomerById(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.listCustomerById(customerId));
    }

    @PostMapping
    public ResponseEntity<?> saveCustomer(@Valid @RequestBody CustomerModel customerModel, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        else {
            customerService.saveCustomer(customerModel);
            return ResponseEntity.status(HttpStatus.CREATED).body("The customer has been created successfully.");
        }
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId)
    {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body("The customer has been successfully deleted.");
    }

    @PutMapping()
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody CustomerModel customerModel, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        else {
            customerService.updateCustomer(customerModel);
            return ResponseEntity.status(HttpStatus.CREATED).body("The customer has been successfully modified.");
        }
    }
}