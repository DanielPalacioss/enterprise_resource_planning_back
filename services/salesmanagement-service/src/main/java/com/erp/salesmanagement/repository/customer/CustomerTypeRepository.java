package com.erp.salesmanagement.repository.customer;

import com.erp.salesmanagement.model.customer.CustomerTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerTypeModel, Long> {
    Optional<List<CustomerTypeModel>> findAllByStatus_Id(Boolean status);
}
