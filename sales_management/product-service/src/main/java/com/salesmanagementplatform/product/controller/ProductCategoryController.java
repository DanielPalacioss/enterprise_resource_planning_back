package com.salesmanagementplatform.product.controller;

import com.salesmanagementplatform.product.error.DataValidation;
import com.salesmanagementplatform.product.model.ProductCategoryModel;
import com.salesmanagementplatform.product.service.ProductCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/productCategory")
@CrossOrigin(origins = "http://localhost:8083")
public class ProductCategoryController {
    @Autowired
    ProductCategoryService productCategoryService;

    DataValidation dataValidation = new DataValidation();

    @GetMapping("list/{status}")
    public ResponseEntity<List<ProductCategoryModel>> getAllProductCategories(@PathVariable String status) {
        return ResponseEntity.ok(productCategoryService.listOfAllProductCategory(status.replaceAll(" ","").toLowerCase()));
    }

    @GetMapping("/{productCategoryId}")
    public ResponseEntity<ProductCategoryModel> getProductCategoryById(@PathVariable Long productCategoryId) {
        return ResponseEntity.ok(productCategoryService.listProductCategoryById(productCategoryId));
    }

    @PostMapping
    public ResponseEntity<?> saveProductCategory(@Valid @RequestBody ProductCategoryModel productCategoryModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        productCategoryService.saveProductCategory(productCategoryModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The product category has been created successfully.");
    }

    @DeleteMapping("/{productCategoryId}")
    public ResponseEntity<?> deleteProductCategory(@PathVariable Long productCategoryId)
    {
        productCategoryService.deleteProductCategory(productCategoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body("The product category has been successfully deleted.");
    }

    @PutMapping()
    public ResponseEntity<?> updateProductCategory(@Valid @RequestBody ProductCategoryModel productCategoryModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        productCategoryService.updateProductCategory(productCategoryModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The product category has been successfully modified.");
    }
}
