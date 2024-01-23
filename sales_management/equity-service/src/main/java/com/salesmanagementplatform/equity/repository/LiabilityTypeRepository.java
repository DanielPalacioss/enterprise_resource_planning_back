package com.salesmanagementplatform.equity.repository;

import com.salesmanagementplatform.equity.model.LiabilityStatusModel;
import com.salesmanagementplatform.equity.model.LiabilityTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiabilityTypeRepository extends JpaRepository<LiabilityTypeModel, Long> {

    LiabilityTypeModel findByLiabilityType(String type);
    List<LiabilityTypeModel> findAllByStatus_Id(Boolean status);
}
