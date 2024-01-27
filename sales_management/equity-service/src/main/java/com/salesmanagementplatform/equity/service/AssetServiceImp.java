package com.salesmanagementplatform.equity.service;

import com.salesmanagementplatform.equity.error.exceptions.RequestException;
import com.salesmanagementplatform.equity.model.AssetModel;
import com.salesmanagementplatform.equity.model.AssetStatusModel;
import com.salesmanagementplatform.equity.model.AssetTypeModel;
import com.salesmanagementplatform.equity.repository.AssetRepository;
import com.salesmanagementplatform.equity.repository.AssetStatusRepository;
import com.salesmanagementplatform.equity.repository.AssetTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssetServiceImp implements AssetService{

    private static final Logger logger = LoggerFactory.getLogger(AssetTypeServiceImp.class);

    private final AssetRepository assetRepository;
    private final AssetStatusRepository assetStatusRepository;
    private final AssetTypeRepository assetTypeRepository;

    public AssetServiceImp(AssetRepository assetRepository, AssetStatusRepository assetStatusRepository, AssetTypeRepository assetTypeRepository) {
        this.assetRepository = assetRepository;
        this.assetStatusRepository = assetStatusRepository;
        this.assetTypeRepository = assetTypeRepository;
    }

    @Override
    public List<AssetModel> filterAssetSearch(FilterFields filterFields) {
        logger.info("Start search for all Asset");
        List<AssetModel> assetList = new ArrayList<AssetModel>();
        assetList = filterFields.getAllAsset();
        if(assetList.isEmpty()) throw new RequestException("The list with the data entered is empty, however validate that the data is correct.","404-Not Found");
        return assetList;
    }

    @Override
    public List<AssetModel> listAllAsset() {
        logger.info("Start search for all Asset");
        List<AssetModel> assetList = assetRepository.findAll();
        if(assetList.isEmpty()) throw new RequestException("The asset list is empty.","100-Continue");
        return assetList;
    }

    @Override
    public void updateAsset(AssetModel updateAsset) {
        AssetModel asset = assetRepository.findById(updateAsset.getId()).orElseThrow(() -> new RequestException("Asset not found with id " +updateAsset.getId(),"404-Not Found"));
        AssetStatusModel assetStatus =assetStatusRepository.findByAssetStatus(updateAsset.getAssetStatus().getAssetStatus());
        if(assetStatus == null) throw new RequestException("Asset Status not found with Status " + updateAsset.getAssetStatus().getAssetStatus(),"404-Not Found");
        AssetTypeModel assetType = assetTypeRepository.findByAssetType(updateAsset.getAssetType().getAssetType());
        if(assetType == null ) throw new RequestException("Asset type not found with Status " + updateAsset.getAssetType().getAssetType(),"404-Not Found");
        asset.setReference(updateAsset.getReference());
        asset.setDescription(updateAsset.getDescription());
        asset.setValue(updateAsset.getValue());
        asset.setUpdateDate(LocalDateTime.now());
        asset.setExpirationDate(updateAsset.getExpirationDate());
        logger.info("Start the update of asset");
        assetRepository.save(asset);
    }

    @Override
    public void saveAsset(AssetModel asset) {
        if(asset.getId() == null){
            AssetStatusModel assetStatus =assetStatusRepository.findByAssetStatus(asset.getAssetStatus().getAssetStatus());
            if(assetStatus == null) throw new RequestException("Asset Status not found with Status " + asset.getAssetStatus().getAssetStatus(),"404-Not Found");
            AssetTypeModel assetType = assetTypeRepository.findByAssetType(asset.getAssetType().getAssetType());
            if(assetType == null ) throw new RequestException("Asset type not found with Status " + asset.getAssetType().getAssetType(),"404-Not Found");
            asset.setCreationDate(LocalDateTime.now());
            asset.setUpdateDate(null);
            logger.info("Start the creation of asset");
            assetRepository.save(asset);
        }
        else throw new RequestException("The asset id must be null", "400-Bad Request");
    }
}
