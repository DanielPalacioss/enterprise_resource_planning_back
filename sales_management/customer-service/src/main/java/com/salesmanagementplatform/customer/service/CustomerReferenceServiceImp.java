package com.salesmanagementplatform.customer.service;

import com.salesmanagementplatform.customer.error.exceptions.RequestException;
import com.salesmanagementplatform.customer.model.CustomerReferenceModel;
import com.salesmanagementplatform.customer.model.Status;
import com.salesmanagementplatform.customer.repository.CustomerReferenceRepository;
import com.salesmanagementplatform.customer.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerReferenceServiceImp implements CustomerReferenceService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerReferenceServiceImp.class);
    private final CustomerReferenceRepository customerReferenceRepository;
    private final StatusRepository statusRepository;

    public CustomerReferenceServiceImp(CustomerReferenceRepository customerReferenceRepository, StatusRepository statusRepository) {
        this.customerReferenceRepository = customerReferenceRepository;
        this.statusRepository = statusRepository;
    }


    @Override
    public List<CustomerReferenceModel> listOfAllCustomersReference(String status) {
        logger.info("Start search for all customer Reference ");
        List<CustomerReferenceModel> customerReferenceList= new ArrayList<CustomerReferenceModel>();
        if(status.replaceAll(" ","").equalsIgnoreCase("active")) {
            customerReferenceList= customerReferenceRepository.findAllByStatus_Id(true);
            if (customerReferenceList.isEmpty()) throw new RequestException("La lista de referencia de clientes en estado '"+status+"' está vacía","100-Continue");
        }
        else if (status.replaceAll(" ","").equalsIgnoreCase("inactive")) {
            customerReferenceList= customerReferenceRepository.findAllByStatus_Id(false);
            if (customerReferenceList.isEmpty()) throw new RequestException("La lista de referencia de clientes en estado '"+status+"' está vacía","100-Continue");
        }
        if (customerReferenceList.isEmpty()) throw new RequestException("No existe el estado: '"+status+"' en la referencia de clientes","100-Continue");
        return customerReferenceList;
    }

    @Override
    public void updateCustomerReference(CustomerReferenceModel updatedCustomerReference) {
        CustomerReferenceModel customerReferenceModel = customerReferenceRepository.findById(updatedCustomerReference.getId()).orElseThrow(()-> new RequestException("Customer reference not found with id " + updatedCustomerReference.getId(),"404-Not Found"));
        customerReferenceModel.setReference(updatedCustomerReference.getReference());
        customerReferenceModel.setDescription(updatedCustomerReference.getDescription());
        customerReferenceModel.setUpdateDate(LocalDateTime.now());
        logger.info("Start the modification of customer category");
        customerReferenceRepository.save(customerReferenceModel);
    }

    @Override
    public void deleteCustomerReference(Long customerReferenceId) {
        CustomerReferenceModel customerReferenceModel = customerReferenceRepository.findById(customerReferenceId).orElseThrow(()-> new RequestException("Customer reference not found with id " + customerReferenceId,"404-Not Found"));
        Status status = statusRepository.findById(false).orElseThrow(() -> new RequestException("Status not found with id false", "404-Not Found"));
        customerReferenceModel.setStatus(status);
        customerReferenceModel.setUpdateDate(LocalDateTime.now());
        logger.info("Start deleting customer reference");
        customerReferenceRepository.save(customerReferenceModel);
    }

    @Override
    public void saveCustomerReference(CustomerReferenceModel customerReferenceModel) {
        if(customerReferenceModel.getId()== null) {
            Status status = statusRepository.findById(true).orElseThrow(() -> new RequestException("Status not found with id true", "404-Not Found"));
            customerReferenceModel.setStatus(status);
            customerReferenceModel.setCreationDate(LocalDateTime.now());
            customerReferenceModel.setUpdateDate(null);
            logger.info("Start the creation of customer reference");
            customerReferenceRepository.save(customerReferenceModel);
        }
        else throw new RequestException("The customer reference id must be null", "400-Bad Request");
    }
}
