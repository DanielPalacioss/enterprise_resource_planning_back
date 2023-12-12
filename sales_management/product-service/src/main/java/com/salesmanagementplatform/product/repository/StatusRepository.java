package com.salesmanagementplatform.product.repository;

import com.salesmanagementplatform.product.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status,Boolean> {
}
