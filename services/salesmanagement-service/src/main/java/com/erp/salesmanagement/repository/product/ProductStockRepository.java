package com.erp.salesmanagement.repository.product;

import com.erp.salesmanagement.model.product.ProductStockModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductStockRepository extends JpaRepository<ProductStockModel, Long> {
    Optional<ProductStockModel> findByProduct_productNumber(int productNumber);
}
