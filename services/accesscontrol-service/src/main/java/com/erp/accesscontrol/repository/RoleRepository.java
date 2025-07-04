package com.erp.accesscontrol.repository;

import com.erp.accesscontrol.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel,Long> {
    Optional<List<RoleModel>> findAllByStatus_Id(Boolean status);
}
