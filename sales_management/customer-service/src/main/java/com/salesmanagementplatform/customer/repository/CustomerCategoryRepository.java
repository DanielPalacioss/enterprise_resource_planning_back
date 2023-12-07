package com.salesmanagementplatform.customer.repository;

import com.salesmanagementplatform.customer.model.CustomerCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerCategoryRepository extends JpaRepository<CustomerCategoryModel, Long> {
    List<CustomerCategoryModel> findAllByStatus_Id(Boolean status);
}
