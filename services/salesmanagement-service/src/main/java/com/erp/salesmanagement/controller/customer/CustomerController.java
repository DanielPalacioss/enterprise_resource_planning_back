package com.erp.salesmanagement.controller.customer;

import com.erp.salesmanagement.error.DataValidation;
import com.erp.salesmanagement.model.customer.CustomerModel;
import com.erp.salesmanagement.service.customer.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
@CrossOrigin(origins = "http://localhost:8082")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    DataValidation dataValidation = new DataValidation();

    @GetMapping("list/{status}")
    public ResponseEntity<List<CustomerModel>> getAllCustomers(@PathVariable String status) {
        return ResponseEntity.ok(customerService.listOfAllCustomers(status.replaceAll(" ","").toLowerCase()));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerModel> getCustomerById(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.listCustomerById(customerId));
    }

    @PostMapping
    public ResponseEntity<?> saveCustomer(@Valid @RequestBody CustomerModel customerModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        customerService.saveCustomer(customerModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The customer has been created successfully.");
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
        dataValidation.handleValidationError(bindingResult);
        customerService.updateCustomer(customerModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The customer has been successfully modified.");
    }
}
