package com.erp.salesmanagement.repository.product;

import com.erp.salesmanagement.model.product.ProductStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStatusRepository extends JpaRepository<ProductStatusModel, Long> {
    ProductStatusModel findByStatus(String status);
}
