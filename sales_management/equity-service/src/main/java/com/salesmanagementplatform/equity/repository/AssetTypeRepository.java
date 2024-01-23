package com.salesmanagementplatform.equity.repository;

import com.salesmanagementplatform.equity.model.AssetTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetTypeRepository extends JpaRepository<AssetTypeModel, Long> {

    AssetTypeModel findByAssetType(String type);
    List<AssetTypeModel> findAllByStatus_Id(Boolean status);
}
