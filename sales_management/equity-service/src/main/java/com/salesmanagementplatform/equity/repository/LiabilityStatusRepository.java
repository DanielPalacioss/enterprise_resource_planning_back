package com.salesmanagementplatform.equity.repository;

import com.salesmanagementplatform.equity.model.LiabilityStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiabilityStatusRepository extends JpaRepository<LiabilityStatusModel, Long> {

    LiabilityStatusModel findByLiabilityStatus(String status);
}
