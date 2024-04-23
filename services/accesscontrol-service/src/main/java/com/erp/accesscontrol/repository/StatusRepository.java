package com.erp.accesscontrol.repository;

import com.erp.accesscontrol.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status,Boolean> {}
