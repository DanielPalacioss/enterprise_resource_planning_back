package com.salesmanagementplatform.map.repository;

import com.salesmanagementplatform.map.model.TownModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TownRepository extends JpaRepository<TownModel, Long> {
    List<TownModel> findAllByDepartmentId(String department);
}
