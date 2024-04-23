package com.erp.salesmanagement.repository.product;

import com.erp.salesmanagement.model.product.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {
    Optional<List<ProductModel>> findAllByProductStatus_status(String status);
}
