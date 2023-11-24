package com.salesmanagementplatform.customer.repository;

import com.salesmanagementplatform.customer.model.CustomerCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCategoryRepository extends JpaRepository<CustomerCategoryModel, Long> {
}
