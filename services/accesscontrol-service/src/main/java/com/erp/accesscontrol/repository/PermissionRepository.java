package com.erp.accesscontrol.repository;

import com.erp.accesscontrol.model.PermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionModel, Long> {
    Optional<List<PermissionModel>> findAllByStatus_Id(Boolean status);
}
