package com.erp.salesmanagement.repository.product;

import com.erp.salesmanagement.model.product.ProductCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryModel, Long> {
    List<ProductCategoryModel> findAllByStatus_Id(Boolean status);
}
