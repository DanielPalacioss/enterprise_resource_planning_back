package com.erp.salesmanagement.repository.product;

import com.erp.salesmanagement.model.product.ProductStockModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockRepository extends JpaRepository<ProductStockModel, Long> {
    ProductStockModel findByProduct_productNumber(int productNumber);
}
