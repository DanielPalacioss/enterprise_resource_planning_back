package com.erp.salesmanagement.repository.product;

import com.erp.salesmanagement.model.product.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {
    List<ProductModel> findAllByProductStatus_status(String status);
}
