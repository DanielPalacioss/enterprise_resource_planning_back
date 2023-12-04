package com.salesmanagementplatform.product.repository;

import com.salesmanagementplatform.product.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {
}
