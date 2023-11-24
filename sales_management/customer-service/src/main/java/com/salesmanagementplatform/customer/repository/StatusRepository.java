package com.salesmanagementplatform.customer.repository;

import com.salesmanagementplatform.customer.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status,Boolean> {
}
