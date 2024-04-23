package com.erp.salesmanagement.repository.product;

import com.erp.salesmanagement.model.product.ProductStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductStatusRepository extends JpaRepository<ProductStatusModel, Long> {
    Optional<ProductStatusModel> findByStatus(String status);
}
