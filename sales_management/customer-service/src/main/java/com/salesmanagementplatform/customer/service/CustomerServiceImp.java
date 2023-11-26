package com.salesmanagementplatform.customer.service;

import com.salesmanagementplatform.customer.error.exceptions.RequestException;
import com.salesmanagementplatform.customer.model.CustomerModel;
import com.salesmanagementplatform.customer.model.Status;
import com.salesmanagementplatform.customer.repository.CustomerRepository;
import com.salesmanagementplatform.customer.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService{

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImp.class);
    private final CustomerRepository customerRepository;
    private final StatusRepository statusRepository;

    public CustomerServiceImp(CustomerRepository customerRepository, StatusRepository statusRepository) {
        this.customerRepository = customerRepository;
        this.statusRepository = statusRepository;
    }


    @Override
    public List<CustomerModel> listOfAllCustomers() {
        logger.info("Start search for all customers");
        return customerRepository.findAll();
    }

    @Override
    public CustomerModel listCustomerById(Long customerId) {
        CustomerModel customerModel = customerRepository.findById(customerId).orElseThrow(() -> new RequestException("Customer not found with id " + customerId,"404-Not Found"));
        logger.info("starting search by customer id");
        return customerModel;
    }

    @Override
    public void updateCustomer(CustomerModel updatedCustomer) {
        CustomerModel customerModel = customerRepository.findById(updatedCustomer.getId()).orElseThrow(() -> new RequestException("Customer not found with id " + updatedCustomer.getId(),"404-Not Found"));
        customerModel.setUpdateDate(LocalDateTime.now());
        customerModel.setCustomerCategory(updatedCustomer.getCustomerCategory());
        customerModel.setCustomerReference(updatedCustomer.getCustomerReference());
        customerModel.setCustomerType(updatedCustomer.getCustomerType());
        customerModel.setAddress(updatedCustomer.getAddress());
        customerModel.setAddress2(updatedCustomer.getAddress2());
        customerModel.setCompanyName(updatedCustomer.getCompanyName());
        customerModel.setEmail(updatedCustomer.getEmail());
        customerModel.setFullName(updatedCustomer.getFullName());
        customerModel.setLastName(updatedCustomer.getLastName());
        customerModel.setTelephone(updatedCustomer.getTelephone());
        logger.info("Start the modification of customer");
        customerRepository.save(customerModel);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        CustomerModel customerModel = customerRepository.findById(customerId).orElseThrow(() -> new RequestException("Customer not found with id " + customerId, "404-Not Found"));
        Status status = statusRepository.findById(false).orElseThrow(() -> new RequestException("Customer not found with id false", "404-Not Found"));
        customerModel.setStatus(status);
        logger.info("Start deleting customer");
        customerRepository.save(customerModel);
    }

    @Override
    public void saveCustomer(CustomerModel customerModel) {
        if(customerRepository.findById(customerModel.getId()).isEmpty())
        {
            Status status = statusRepository.findById(true).orElseThrow(() -> new RequestException("Customer not found with id true", "404-Not Found"));
            customerModel.setStatus(status);
            customerModel.setCreationDate(LocalDateTime.now());
            logger.info("Start the creation of customer");
            customerRepository.save(customerModel);
        }
        throw new RequestException("The customer with id '" + customerModel.getId() +"' is already in the database", "400-Bad Request");
    }
}
