package com.erp.salesmanagement.controller.customer;

import com.erp.salesmanagement.error.DataValidation;
import com.erp.salesmanagement.model.customer.CustomerCategoryModel;
import com.erp.salesmanagement.service.customer.CustomerCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sm/customerCategory")
@CrossOrigin(origins = "http://localhost:8082")
public class CustomerCategoryController {

    @Autowired
    CustomerCategoryService customerCategoryService;

    DataValidation dataValidation = new DataValidation();
    @GetMapping("/{status}")
    public ResponseEntity<List<CustomerCategoryModel>> getAllCustomersCategory(@PathVariable String status) {
        return ResponseEntity.ok(customerCategoryService.listOfAllCustomersCategory(status));
    }

    @PostMapping
    public ResponseEntity<?> saveCustomerCategory(@Valid @RequestBody CustomerCategoryModel customerCategoryModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        customerCategoryService.saveCustomerCategory(customerCategoryModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The customer category has been created successfully.");
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
        dataValidation.handleValidationError(bindingResult);
        customerCategoryService.updateCustomerCategory(customerCategoryModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The customer category has been successfully modified.");

    }
}
