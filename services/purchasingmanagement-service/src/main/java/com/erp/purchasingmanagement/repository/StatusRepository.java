package com.erp.purchasingmanagement.repository;

import com.erp.purchasingmanagement.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status,Boolean> {}
