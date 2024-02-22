package com.erp.salesmanagement.controller.customer;

import com.erp.salesmanagement.error.DataValidation;
import com.erp.salesmanagement.model.customer.CustomerTypeModel;
import com.erp.salesmanagement.service.customer.CustomerTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sm/customerType")
@CrossOrigin(origins = "http://localhost:8082")
public class CustomerTypeController {

    @Autowired
    CustomerTypeService customerTypeService;

    DataValidation dataValidation = new DataValidation();
    @GetMapping("/{status}")
    public ResponseEntity<List<CustomerTypeModel>> getAllCustomersType(@PathVariable String status) {
        return ResponseEntity.ok(customerTypeService.listOfAllCustomersType(status.replaceAll(" ","").toLowerCase()));
    }

    @PostMapping
    public ResponseEntity<?> saveCustomerType(@Valid @RequestBody CustomerTypeModel customerTypeModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        customerTypeService.saveCustomerType(customerTypeModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The customer type has been created successfully.");

    }

    @DeleteMapping("/{customerTypeId}")
    public ResponseEntity<?> deleteCustomerType(@PathVariable Long customerTypeId)
    {
        customerTypeService.deleteCustomerType(customerTypeId);
        return ResponseEntity.status(HttpStatus.CREATED).body("The customer type has been successfully deleted.");
    }

    @PutMapping
    public ResponseEntity<?> updateCustomerReference(@Valid @RequestBody CustomerTypeModel customerTypeModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        customerTypeService.updateCustomerType(customerTypeModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The customer Reference has been successfully modified.");

    }
}
