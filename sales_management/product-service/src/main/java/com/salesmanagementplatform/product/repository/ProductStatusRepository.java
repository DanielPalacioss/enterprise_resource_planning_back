package com.salesmanagementplatform.product.repository;

import com.salesmanagementplatform.product.model.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStatusRepository extends JpaRepository<ProductStatus, Long> {
}
