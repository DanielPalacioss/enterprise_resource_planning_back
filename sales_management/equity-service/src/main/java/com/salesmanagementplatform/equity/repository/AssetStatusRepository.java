package com.salesmanagementplatform.equity.repository;

import com.salesmanagementplatform.equity.model.AssetStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetStatusRepository extends JpaRepository<AssetStatusModel, Long> {

    AssetStatusModel findByAssetStatus (String status);
}
