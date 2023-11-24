package com.salesmanagementplatform.customer.repository;

import com.salesmanagementplatform.customer.model.CustomerReferenceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerReferenceRepository extends JpaRepository<CustomerReferenceModel, Long> {
}
