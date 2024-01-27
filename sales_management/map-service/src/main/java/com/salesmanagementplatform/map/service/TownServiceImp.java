package com.salesmanagementplatform.map.service;

import com.salesmanagementplatform.map.error.exceptions.RequestException;
import com.salesmanagementplatform.map.model.TownModel;
import com.salesmanagementplatform.map.repository.TownRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TownServiceImp implements TownService{

    private final Logger logger = LoggerFactory.getLogger(TownServiceImp.class);
    private final TownRepository townRepository;

    public TownServiceImp(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    @Override
    public List<TownModel> listAllTown(String department) {
        logger.info("Start search for all town");
        List<TownModel> townList = townRepository.findAllByDepartmentId(department);
        if(townList.isEmpty()) throw new RequestException("The town list is empty.","100-Continue");
        return townList;
    }
}
