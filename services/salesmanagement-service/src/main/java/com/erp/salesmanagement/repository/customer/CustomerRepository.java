package com.erp.salesmanagement.repository.customer;

import com.erp.salesmanagement.model.customer.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {
    Optional<List<CustomerModel>> findAllByStatus_Id(Boolean status);
}