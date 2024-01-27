package com.salesmanagementplatform.equity.service;

import com.salesmanagementplatform.equity.error.exceptions.RequestException;
import com.salesmanagementplatform.equity.model.*;
import com.salesmanagementplatform.equity.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LiabilityServiceImp implements LiabilityService{

    Logger logger = LoggerFactory.getLogger(LiabilityServiceImp.class);

    private final LiabilityRepository liabilityRepository;
    private final LiabilityStatusRepository liabilityStatusRepository;
    private final LiabilityTypeRepository liabilityTypeRepository;

    public LiabilityServiceImp(LiabilityRepository liabilityRepository, LiabilityStatusRepository liabilityStatusRepository, LiabilityTypeRepository liabilityTypeRepository) {
        this.liabilityRepository = liabilityRepository;
        this.liabilityStatusRepository = liabilityStatusRepository;
        this.liabilityTypeRepository = liabilityTypeRepository;
    }

    @Override
    public List<LiabilityModel> filterLiabilitySearch(FilterFields filterFields) {
        logger.info("Start search for all liability");
        List<LiabilityModel> liabilityList = new ArrayList<LiabilityModel>();
        liabilityList = filterFields.getAllLiability();
        if(liabilityList.isEmpty()) throw new RequestException("The list with the data entered is empty, however validate that the data is correct.","404-Not Found");
        return liabilityList;
    }

    @Override
    public List<LiabilityModel> listAllLiability() {
        logger.info("Start search for all liability");
        List<LiabilityModel> liabilityList = liabilityRepository.findAll();
        if(liabilityList.isEmpty()) throw new RequestException("The liability list is empty.","100-Continue");
        return liabilityList;
    }

    @Override
    public void updateLiability(LiabilityModel updateLiability) {
        LiabilityModel liability = liabilityRepository.findById(updateLiability.getId()).orElseThrow(() -> new RequestException("Asset not found with id " +updateLiability.getId(),"404-Not Found"));
        LiabilityStatusModel liabilityStatus = liabilityStatusRepository.findByLiabilityStatus(updateLiability.getLiabilityStatus().getLiabilityStatus());
        if(liabilityStatus == null) throw new RequestException("Liability Status not found with Status " + updateLiability.getLiabilityStatus().getLiabilityStatus(),"404-Not Found");
        LiabilityTypeModel liabilityType = liabilityTypeRepository.findByLiabilityType(updateLiability.getLiabilityType().getLiabilityType());
        if(liabilityType == null ) throw new RequestException("Liability type not found with Status " + updateLiability.getLiabilityType().getLiabilityType(),"404-Not Found");
        liability.setReference(updateLiability.getReference());
        liability.setDescription(updateLiability.getDescription());
        liability.setValue(updateLiability.getValue());
        liability.setUpdateDate(LocalDateTime.now());
        liability.setExpirationDate(updateLiability.getExpirationDate());
        logger.info("Start the update of liability");
        liabilityRepository.save(liability);
    }

    @Override
    public void saveLiability(LiabilityModel liability) {
        if(liability.getId() == null){
            LiabilityStatusModel liabilityStatus = liabilityStatusRepository.findByLiabilityStatus(liability.getLiabilityStatus().getLiabilityStatus());
            if(liabilityStatus == null) throw new RequestException("Liability Status not found with Status " + liability.getLiabilityStatus().getLiabilityStatus(),"404-Not Found");
            LiabilityTypeModel liabilityType = liabilityTypeRepository.findByLiabilityType(liability.getLiabilityType().getLiabilityType());
            if(liabilityType == null ) throw new RequestException("Liability type not found with Status " + liability.getLiabilityType().getLiabilityType(),"404-Not Found");
            liability.setCreationDate(LocalDateTime.now());
            liability.setUpdateDate(null);
            logger.info("Start the creation of liability");
            liabilityRepository.save(liability);
        }
        else throw new RequestException("The liability id must be null", "400-Bad Request");
    }
}
