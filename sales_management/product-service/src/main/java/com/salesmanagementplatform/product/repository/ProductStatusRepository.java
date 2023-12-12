package com.salesmanagementplatform.product.repository;

import com.salesmanagementplatform.product.model.ProductStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStatusRepository extends JpaRepository<ProductStatusModel, Long> {
    ProductStatusModel findByStatus(String status);
}
