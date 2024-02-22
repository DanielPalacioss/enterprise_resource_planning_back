package com.erp.salesmanagement.controller.product;

import com.erp.salesmanagement.error.DataValidation;
import com.erp.salesmanagement.model.product.ProductModel;
import com.erp.salesmanagement.model.product.ProductStockModel;
import com.erp.salesmanagement.service.product.ProductStockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sm/productStock")
@CrossOrigin(origins = "http://localhost:8082")
public class ProductStockController {
    @Autowired
    ProductStockService productStockService;
    DataValidation dataValidation = new DataValidation();

    @GetMapping("/{productNumber}")
    public ResponseEntity<ProductStockModel> getProductStockByProduct_ProductNumber(@PathVariable int productNumber) {
        return ResponseEntity.ok(productStockService.listProductStockByProductId(productNumber));
    }

    @PostMapping
    public ResponseEntity<?> saveProductStock(@Valid @RequestBody ProductStockModel productStockModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        productStockService.saveProductStock(productStockModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The product stock has been created successfully.");
    }

    @DeleteMapping
    public ResponseEntity<?> reduceProductStock(@Valid @RequestBody List<ProductModel> productList, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        productStockService.reduceStock(productList);
        return ResponseEntity.status(HttpStatus.CREATED).body("The stock of the products or the product sold was satisfactorily reduced.");
    }

    @PutMapping("/cancellationOfStockReduction")
    public ResponseEntity<?> cancellationOfStockReduction(@Valid @RequestBody List<ProductModel> productList, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        productStockService.cancellationOfStockReduction(productList);
        return ResponseEntity.status(HttpStatus.CREATED).body("The stock of the products or the product sold was satisfactorily cancellation of stock reduction.");
    }

    @PutMapping
    public ResponseEntity<?> updateProductStock(@Valid @RequestBody ProductStockModel productStockModel, BindingResult bindingResult)
    {
        dataValidation.handleValidationError(bindingResult);
        productStockService.updateProductStock(productStockModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The product stock has been successfully modified.");
    }
}
