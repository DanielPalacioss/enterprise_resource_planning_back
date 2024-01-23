package com.salesmanagementplatform.equity.service;

import com.salesmanagementplatform.equity.model.AssetModel;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.List;

public interface AssetService {

    List<AssetModel> filterAssetSearch(FilterFields filterFields);

    List<AssetModel> listAllAsset();

    void updateAsset(AssetModel updateAsset);

    void saveAsset(AssetModel asset);
}
