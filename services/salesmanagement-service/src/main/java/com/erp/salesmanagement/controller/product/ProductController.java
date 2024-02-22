package com.erp.salesmanagement.controller.product;

import com.erp.salesmanagement.error.DataValidation;
import com.erp.salesmanagement.model.product.ProductModel;
import com.erp.salesmanagement.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sm/product")
@CrossOrigin(origins = "http://localhost:8082")
public class ProductController {
    @Autowired
    private ProductService productService;

    private static final DataValidation dataValidation = new DataValidation();

    @GetMapping("list/{status}")
    public ResponseEntity<List<ProductModel>> getAllProducts(@PathVariable String status) {
        return ResponseEntity.ok(productService.listOfAllProduct(status));
    }

    @GetMapping("/{productNumber}")
    public ResponseEntity<ProductModel> getProductById(@PathVariable int productNumber) {
        return ResponseEntity.ok(productService.listProductById(productNumber));
    }

    @PostMapping
    public ResponseEntity<?> saveProduct(@Valid @RequestBody ProductModel product, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("The product has been created successfully.");
    }

    @DeleteMapping("/{productNumber}")
    public ResponseEntity<?> deleteProduct(@PathVariable int productNumber)
    {
        productService.deleteProduct(productNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body("The product has been successfully deleted.");
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody ProductModel productModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        productService.updateProduct(productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The product has been successfully modified.");
    }

}
