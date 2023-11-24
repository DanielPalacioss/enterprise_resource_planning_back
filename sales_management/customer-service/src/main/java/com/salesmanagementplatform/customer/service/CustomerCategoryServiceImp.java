package com.salesmanagementplatform.customer.service;

import com.salesmanagementplatform.customer.model.CustomerCategoryModel;
import com.salesmanagementplatform.customer.model.Status;
import com.salesmanagementplatform.customer.repository.CustomerCategoryRepository;
import com.salesmanagementplatform.customer.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerCategoryServiceImp implements CustomerCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerCategoryServiceImp.class);
    private final CustomerCategoryRepository customerCategoryRepository;
    private final StatusRepository statusRepository;

    public CustomerCategoryServiceImp(CustomerCategoryRepository customerCategoryRepository, StatusRepository statusRepository) {
        this.customerCategoryRepository = customerCategoryRepository;
        this.statusRepository = statusRepository;
    }


    @Override
    public List<CustomerCategoryModel> listOfAllCustomersCategory() {
        logger.info("Start search for all customers category ");
        return customerCategoryRepository.findAll();
    }

    @Override
    public void updateCustomerCategory(CustomerCategoryModel updatedCustomerCategory) {
        CustomerCategoryModel customerCategoryModel = customerCategoryRepository.findById(updatedCustomerCategory.getId()).orElseThrow();// -> new ResourceNotFoundException("Customer not found with id " + updatedCustomer.getId()));
        customerCategoryModel.setCategory(updatedCustomerCategory.getCategory());
        customerCategoryModel.setDescription(updatedCustomerCategory.getDescription());
        customerCategoryModel.setUpdateDate(LocalDateTime.now());
        customerCategoryRepository.save(customerCategoryModel);
    }

    @Override
    public void deleteCustomerCategory(Long customerCategoryId) {
        CustomerCategoryModel customerCategoryModel = customerCategoryRepository.findById(customerCategoryId).orElseThrow();// -> new ResourceNotFoundException("Customer not found with id " + customerId));
        Status status = statusRepository.findById(false).orElseThrow();// -> new ResourceNotFoundException("Customer not found with id " + false));
        customerCategoryModel.setStatus(status);
        customerCategoryRepository.save(customerCategoryModel);
    }

    @Override
    public void saveCustomerCategory(CustomerCategoryModel customerCategoryModel) {
        Status status = statusRepository.findById(true).orElseThrow();// -> new ResourceNotFoundException("Customer not found with id " + false));
        customerCategoryModel.setStatus(status);
        customerCategoryModel.setCreationDate(LocalDateTime.now());
        customerCategoryRepository.save(customerCategoryModel);
    }

}
