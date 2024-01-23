package com.salesmanagementplatform.invoices.repository;

import com.salesmanagementplatform.invoices.model.customer.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status,Boolean> {
}
