package com.salesmanagementplatform.customer.repository;

import com.salesmanagementplatform.customer.model.CustomerTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerTypeModel, Long> {
}
