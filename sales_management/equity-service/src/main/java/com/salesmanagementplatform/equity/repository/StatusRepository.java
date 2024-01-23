package com.salesmanagementplatform.equity.repository;

import com.salesmanagementplatform.equity.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status,Boolean> {
}
