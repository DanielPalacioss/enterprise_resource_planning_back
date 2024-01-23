package com.salesmanagementplatform.equity.service;

import com.salesmanagementplatform.equity.error.exceptions.RequestException;
import com.salesmanagementplatform.equity.model.AssetTypeModel;
import com.salesmanagementplatform.equity.model.LiabilityTypeModel;
import com.salesmanagementplatform.equity.model.Status;
import com.salesmanagementplatform.equity.repository.AssetTypeRepository;
import com.salesmanagementplatform.equity.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssetTypeServiceImp implements AssetTypeService {
    private static final Logger logger = LoggerFactory.getLogger(AssetTypeServiceImp.class);
    private final AssetTypeRepository assetTypeRepository;
    private final StatusRepository statusRepository;

    public AssetTypeServiceImp(AssetTypeRepository assetTypeRepository, StatusRepository statusRepository) {
        this.assetTypeRepository = assetTypeRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public List<AssetTypeModel> listAllAssetType(String status) {
        logger.info("Start search for all asset type");
        List<AssetTypeModel> assetTypeList = new ArrayList<AssetTypeModel>();
        if(status.replaceAll(" ","").equalsIgnoreCase("active")) {
            assetTypeList= assetTypeRepository.findAllByStatus_Id(true);
            if (assetTypeList.isEmpty()) throw new RequestException("La lista de asset type en estado '"+status+"' está vacía","100-Continue");
        }
        else if (status.replaceAll(" ","").equalsIgnoreCase("inactive")) {
            assetTypeList= assetTypeRepository.findAllByStatus_Id(false);
            if (assetTypeList.isEmpty()) throw new RequestException("La lista de asset type en estado '"+status+"' está vacía","100-Continue");
        }
        else throw new RequestException("No existe el estado: '"+status+"' en la asset type","100-Continue");
        return assetTypeList;
    }

    @Override
    public void updateAssetType(AssetTypeModel updateAssetType) {
        AssetTypeModel assetType = assetTypeRepository.findById(updateAssetType.getId()).orElseThrow(()-> new RequestException("Asset type not found with id " + updateAssetType.getId(), "404-Not Found"));
        assetType.setAssetType(updateAssetType.getAssetType());
        assetType.setDescription(updateAssetType.getDescription());
        assetType.setUpdateDate(LocalDateTime.now());
        logger.info("Start the update of asset type");
        assetTypeRepository.save(assetType);
    }

    @Override
    public void deleteAssetType(Long assetTypeId)
    {
        AssetTypeModel assetType = assetTypeRepository.findById(assetTypeId).orElseThrow(()-> new RequestException("Asset type not found with id " + assetTypeId, "404-Not Found"));
        Status status = statusRepository.findById(false).orElseThrow(() -> new RequestException("Status not found with id false", "404-Not Found"));
        assetType.setStatus(status);
        assetType.setUpdateDate(LocalDateTime.now());
        assetTypeRepository.save(assetType);
    }

    @Override
    public void saveAssetType(AssetTypeModel assetType) {
        if (assetType.getId() == null)
        {
            Status status = statusRepository.findById(true).orElseThrow(() -> new RequestException("Status not found with id true", "404-Not Found"));
            assetType.setStatus(status);
            assetType.setCreationDate(LocalDateTime.now());
            assetType.setUpdateDate(null);
            logger.info("Start the creation of asset type");
            assetTypeRepository.save(assetType);
        }
        else throw new RequestException("The asset type id must be null", "400-Bad Request");
    }
}
