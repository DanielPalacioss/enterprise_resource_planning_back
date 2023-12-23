package com.salesmanagementplatform.customer.service;

import com.salesmanagementplatform.customer.error.exceptions.RequestException;
import com.salesmanagementplatform.customer.model.CustomerTypeModel;
import com.salesmanagementplatform.customer.model.Status;
import com.salesmanagementplatform.customer.repository.CustomerTypeRepository;
import com.salesmanagementplatform.customer.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<CustomerTypeModel> listOfAllCustomersType(String status) {
        logger.info("Start search for all customers type ");
        List<CustomerTypeModel> customerTypeList= new ArrayList<CustomerTypeModel>();
        if(status.replaceAll(" ","").equalsIgnoreCase("active")) {
            customerTypeList= customerTypeRepository.findAllByStatus_Id(true);
            if (customerTypeList.isEmpty()) throw new RequestException("La lista de type de clientes en estado '"+status+"' está vacía","100-Continue");
        }
        else if (status.replaceAll(" ","").equalsIgnoreCase("inactive")) {
            customerTypeList= customerTypeRepository.findAllByStatus_Id(false);
            if (customerTypeList.isEmpty()) throw new RequestException("La lista de type de clientes en estado '"+status+"' está vacía","100-Continue");
        }
        else throw new RequestException("No existe el estado: '"+status+"' en la type de clientes","100-Continue");
        return customerTypeList;
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
        customerTypeModel.setUpdateDate(LocalDateTime.now());
        logger.info("Start deleting customer type");
        customerTypeRepository.save(customerTypeModel);
    }

    @Override
    public void saveCustomerType(CustomerTypeModel customerTypeModel) {
        if(customerTypeModel.getId() == null) {
            Status status = statusRepository.findById(true).orElseThrow(() -> new RequestException("Status not found with id true", "404-Not Found"));
            customerTypeModel.setStatus(status);
            customerTypeModel.setCreationDate(LocalDateTime.now());
            customerTypeModel.setUpdateDate(null);
            logger.info("Start the creation of customer type");
            customerTypeRepository.save(customerTypeModel);
        }
        else throw new RequestException("The customer type id must be null", "400-Bad Request");
    }
}
