package com.erp.salesmanagement.repository.customer;

import com.erp.salesmanagement.model.customer.CustomerReferenceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerReferenceRepository extends JpaRepository<CustomerReferenceModel, Long> {
    List<CustomerReferenceModel> findAllByStatus_Id(Boolean status);
}
