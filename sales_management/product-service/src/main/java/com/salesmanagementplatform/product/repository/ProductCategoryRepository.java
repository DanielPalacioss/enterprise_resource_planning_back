package com.salesmanagementplatform.product.repository;

import com.salesmanagementplatform.product.model.ProductCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryModel, Long> {
}
