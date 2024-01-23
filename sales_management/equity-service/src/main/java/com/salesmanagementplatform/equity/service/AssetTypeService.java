package com.salesmanagementplatform.equity.service;

import com.salesmanagementplatform.equity.model.AssetTypeModel;

import java.util.List;

public interface AssetTypeService {
    List<AssetTypeModel> listAllAssetType(String status);

    void updateAssetType(AssetTypeModel updateAssetType);

    void deleteAssetType(Long assetTypeId);

    void saveAssetType(AssetTypeModel assetType);
}
