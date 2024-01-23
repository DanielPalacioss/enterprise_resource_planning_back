package com.salesmanagementplatform.equity.service;

import com.salesmanagementplatform.equity.error.exceptions.RequestException;
import com.salesmanagementplatform.equity.model.AssetTypeModel;
import com.salesmanagementplatform.equity.model.LiabilityTypeModel;
import com.salesmanagementplatform.equity.model.Status;
import com.salesmanagementplatform.equity.repository.LiabilityTypeRepository;
import com.salesmanagementplatform.equity.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LiabilityTypeServiceImp implements LiabilityTypeService{
    private static final Logger logger = LoggerFactory.getLogger(AssetTypeServiceImp.class);
    private final LiabilityTypeRepository liabilityTypeRepository;
    private final StatusRepository statusRepository;

    public LiabilityTypeServiceImp(LiabilityTypeRepository liabilityTypeRepository, StatusRepository statusRepository) {
        this.liabilityTypeRepository = liabilityTypeRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public List<LiabilityTypeModel> listAllLiabilityType(String status) {
        logger.info("Start search for all liability type");
        List<LiabilityTypeModel> liabilityTypeList = new ArrayList<LiabilityTypeModel>();
        if(status.replaceAll(" ","").equalsIgnoreCase("active")) {
            liabilityTypeList= liabilityTypeRepository.findAllByStatus_Id(true);
            if (liabilityTypeList.isEmpty()) throw new RequestException("La lista de liability type en estado '"+status+"' está vacía","100-Continue");
        }
        else if (status.replaceAll(" ","").equalsIgnoreCase("inactive")) {
            liabilityTypeList= liabilityTypeRepository.findAllByStatus_Id(false);
            if (liabilityTypeList.isEmpty()) throw new RequestException("La lista de liability type en estado '"+status+"' está vacía","100-Continue");
        }
        else throw new RequestException("No existe el estado: '"+status+"' en la liability type","100-Continue");
        return liabilityTypeList;
    }

    @Override
    public void updateLiabilityType(LiabilityTypeModel updateLiabilityType) {
        LiabilityTypeModel liabilityType = liabilityTypeRepository.findById(updateLiabilityType.getId()).orElseThrow(()-> new RequestException("Liability type not found with id " + updateLiabilityType.getId(), "404-Not Found"));
        liabilityType.setLiabilityType(updateLiabilityType.getLiabilityType());
        liabilityType.setDescription(updateLiabilityType.getDescription());
        liabilityType.setUpdateDate(LocalDateTime.now());
        logger.info("Start the update of liability type");
        liabilityTypeRepository.save(liabilityType);
    }

    @Override
    public void deleteLiabilityType(Long liabilityTypeId)
    {
        LiabilityTypeModel liabilityType = liabilityTypeRepository.findById(liabilityTypeId).orElseThrow(()-> new RequestException("Liability type not found with id " + liabilityTypeId, "404-Not Found"));
        Status status = statusRepository.findById(false).orElseThrow(() -> new RequestException("Status not found with id false", "404-Not Found"));
        liabilityType.setStatus(status);
        liabilityType.setUpdateDate(LocalDateTime.now());
        liabilityTypeRepository.save(liabilityType);
    }

    @Override
    public void saveLiabilityType(LiabilityTypeModel liabilityType) {
        if (liabilityType.getId() == null)
        {
            Status status = statusRepository.findById(true).orElseThrow(() -> new RequestException("Status not found with id true", "404-Not Found"));
            liabilityType.setStatus(status);
            liabilityType.setCreationDate(LocalDateTime.now());
            liabilityType.setUpdateDate(null);
            logger.info("Start the creation of liability type");
            liabilityTypeRepository.save(liabilityType);
        }
        else throw new RequestException("The liability type id must be null", "400-Bad Request");
    }
}
