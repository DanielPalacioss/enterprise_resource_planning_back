package com.salesmanagementplatform.customer.service;

import com.salesmanagementplatform.customer.model.CustomerCategoryModel;
import com.salesmanagementplatform.customer.model.CustomerReferenceModel;
import com.salesmanagementplatform.customer.model.Status;
import com.salesmanagementplatform.customer.repository.CustomerReferenceRepository;
import com.salesmanagementplatform.customer.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<CustomerReferenceModel> listOfAllCustomersReference() {
        logger.info("Start search for all customers Reference ");
        return customerReferenceRepository.findAll();
    }

    @Override
    public void updateCustomerReference(CustomerReferenceModel updatedCustomerReference) {
        CustomerReferenceModel customerReferenceModel = customerReferenceRepository.findById(updatedCustomerReference.getId()).orElseThrow();// -> new ResourceNotFoundException("Customer not found with id " + updatedCustomer.getId()));
        customerReferenceModel.setReference(updatedCustomerReference.getReference());
        customerReferenceModel.setDescription(updatedCustomerReference.getDescription());
        customerReferenceModel.setUpdateDate(LocalDateTime.now());
        customerReferenceRepository.save(customerReferenceModel);
    }

    @Override
    public void deleteCustomerReference(Long customerReferenceId) {
        CustomerReferenceModel customerReferenceModel = customerReferenceRepository.findById(customerReferenceId).orElseThrow();// -> new ResourceNotFoundException("Customer not found with id " + customerId));
        Status status = statusRepository.findById(false).orElseThrow();// -> new ResourceNotFoundException("Customer not found with id " + false));
        customerReferenceModel.setStatus(status);
        customerReferenceRepository.save(customerReferenceModel);
    }

    @Override
    public void saveCustomerReference(CustomerReferenceModel customerReferenceModel) {
        Status status = statusRepository.findById(true).orElseThrow();// -> new ResourceNotFoundException("Customer not found with id " + false));
        customerReferenceModel.setStatus(status);
        customerReferenceModel.setCreationDate(LocalDateTime.now());
        customerReferenceRepository.save(customerReferenceModel);
    }
}
