package com.salesmanagementplatform.product.repository;

import com.salesmanagementplatform.product.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {

    List<ProductModel> findAllByProductStatus_status(String status);
}
