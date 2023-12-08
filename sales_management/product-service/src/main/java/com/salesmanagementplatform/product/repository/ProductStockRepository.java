package com.salesmanagementplatform.product.repository;

import com.salesmanagementplatform.product.model.ProductStockModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockRepository extends JpaRepository<ProductStockModel, Integer> {
    ProductStockModel findByProduct_productNumber(int productNumber);
}
