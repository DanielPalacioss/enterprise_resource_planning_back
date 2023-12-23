package com.salesmanagementplatform.invoices.client;

import com.salesmanagementplatform.invoices.model.product.ProductModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service",url = "http://localhost:8083/api/")
public interface ProductClient {

    @PutMapping(value = "productStock/cancellationOfStockReduction", consumes = MediaType.APPLICATION_JSON_VALUE)
    void cancellationOfStockReduction(@RequestBody List<ProductModel> productList);
}
