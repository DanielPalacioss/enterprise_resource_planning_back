package com.salesmanagementplatform.customer.service;

import com.salesmanagementplatform.customer.error.exceptions.RequestException;
import com.salesmanagementplatform.customer.model.CustomerReferenceModel;
import com.salesmanagementplatform.customer.model.CustomerTypeModel;
import com.salesmanagementplatform.customer.model.Status;
import com.salesmanagementplatform.customer.repository.CustomerTypeRepository;
import com.salesmanagementplatform.customer.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerTypeServiceImp implements CustomerTypeService{
    private static final Logger logger = LoggerFactory.getLogger(CustomerTypeServiceImp.class);
    private final CustomerTypeRepository customerTypeRepository;
    private final StatusRepository statusRepository;

    public CustomerTypeServiceImp(CustomerTypeRepository customerTypeRepository, StatusRepository statusRepository) {
        this.customerTypeRepository = customerTypeRepository;
        this.statusRepository = statusRepository;
    }


    @Override
    public List<CustomerTypeModel> listOfAllCustomersType() {
        logger.info("Start search for all customers type ");
        return customerTypeRepository.findAll();
    }

    @Override
    public void updateCustomerType(CustomerTypeModel updatedCustomerType) {
        CustomerTypeModel customerTypeModel = customerTypeRepository.findById(updatedCustomerType.getId()).orElseThrow(()-> new RequestException("Customer type not found with id " + updatedCustomerType.getId(),"404-Not Found"));
        customerTypeModel.setType(updatedCustomerType.getType());
        customerTypeModel.setDescription(updatedCustomerType.getDescription());
        customerTypeModel.setUpdateDate(LocalDateTime.now());
        logger.info("Start the modification of customer type");
        customerTypeRepository.save(customerTypeModel);
    }

    @Override
    public void deleteCustomerType(Long customerTypeId) {
        CustomerTypeModel customerTypeModel = customerTypeRepository.findById(customerTypeId).orElseThrow(()-> new RequestException("Customer type not found with id " + customerTypeId,"404-Not Found"));
        Status status = statusRepository.findById(false).orElseThrow(() -> new RequestException("Status not found with id false", "404-Not Found"));
        customerTypeModel.setStatus(status);
        logger.info("Start deleting customer type");
        customerTypeRepository.save(customerTypeModel);
    }

    @Override
    public void saveCustomerType(CustomerTypeModel customerTypeModel) {
        Status status = statusRepository.findById(true).orElseThrow(() -> new RequestException("Status not found with id true", "404-Not Found"));
        customerTypeModel.setStatus(status);
        customerTypeModel.setCreationDate(LocalDateTime.now());
        logger.info("Start the creation of customer type");
        customerTypeRepository.save(customerTypeModel);
    }
}
