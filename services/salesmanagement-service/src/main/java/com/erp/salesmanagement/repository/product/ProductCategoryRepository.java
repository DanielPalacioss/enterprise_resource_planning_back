package com.erp.salesmanagement.repository.product;

import com.erp.salesmanagement.model.product.ProductCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryModel, Long> {
    Optional<List<ProductCategoryModel>> findAllByStatus_Id(Boolean status);
}
