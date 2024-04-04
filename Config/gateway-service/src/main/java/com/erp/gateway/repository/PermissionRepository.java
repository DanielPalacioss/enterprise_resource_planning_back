package com.erp.gateway.repository;

import com.erp.gateway.util.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query(value = "SELECT p.id, p.name, p.creation_date, p.update_date FROM sc_business_management_platform.permission p WHERE p.name in(:name)", nativeQuery = true)
    List<Permission> findAllByName(@Param("name") List<String> name);
}
