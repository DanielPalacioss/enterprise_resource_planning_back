package com.salesmanagementplatform.customer.service;

import com.salesmanagementplatform.customer.error.exceptions.RequestException;
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
        logger.info("Start search for all customer category");
        return customerCategoryRepository.findAll();
    }

    @Override
    public void updateCustomerCategory(CustomerCategoryModel updatedCustomerCategory) {
        CustomerCategoryModel customerCategoryModel = customerCategoryRepository.findById(updatedCustomerCategory.getId()).orElseThrow(() -> new RequestException("Customer category not found with id " + updatedCustomerCategory.getId(),"404-Not Found"));
        customerCategoryModel.setCategory(updatedCustomerCategory.getCategory());
        customerCategoryModel.setDescription(updatedCustomerCategory.getDescription());
        customerCategoryModel.setUpdateDate(LocalDateTime.now());
        logger.info("Start the modification of customer category");
        customerCategoryRepository.save(customerCategoryModel);
    }

    @Override
    public void deleteCustomerCategory(Long customerCategoryId) {
        CustomerCategoryModel customerCategoryModel = customerCategoryRepository.findById(customerCategoryId).orElseThrow(() -> new RequestException("Customer category not found with id " + customerCategoryId,"404-Not Found"));
        Status status = statusRepository.findById(false).orElseThrow(() -> new RequestException("Status not found with id false", "404-Not Found"));
        customerCategoryModel.setStatus(status);
        customerCategoryModel.setUpdateDate(LocalDateTime.now());
        logger.info("Start deleting customer category");
        customerCategoryRepository.save(customerCategoryModel);
    }

    @Override
    public void saveCustomerCategory(CustomerCategoryModel customerCategoryModel) {
        Status status = statusRepository.findById(true).orElseThrow(() -> new RequestException("Status not found with id true", "404-Not Found"));
        customerCategoryModel.setStatus(status);
        customerCategoryModel.setCreationDate(LocalDateTime.now());
        customerCategoryModel.setUpdateDate(null);
        logger.info("Start the creation of customer category");
        customerCategoryRepository.save(customerCategoryModel);
    }

}
