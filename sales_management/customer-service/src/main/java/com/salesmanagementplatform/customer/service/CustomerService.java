package com.salesmanagementplatform.customer.service;

import com.salesmanagementplatform.customer.model.CustomerModel;

import java.util.List;

public interface CustomerService {
    List<CustomerModel> listOfAllCustomers();

    CustomerModel listCustomerById(Long customerId);

    void updateCustomer(CustomerModel customerModel);

    void deleteCustomer(Long customerId);

    void saveCustomer(CustomerModel customerModel);
}
