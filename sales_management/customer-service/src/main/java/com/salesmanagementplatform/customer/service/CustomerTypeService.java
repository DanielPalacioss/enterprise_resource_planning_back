package com.salesmanagementplatform.customer.service;

import com.salesmanagementplatform.customer.model.CustomerTypeModel;

import java.util.List;

public interface CustomerTypeService {
    List<CustomerTypeModel> listOfAllCustomersType(String status);

    void updateCustomerType(CustomerTypeModel updatedCustomerType);

    void deleteCustomerType(Long customerTypeId);

    void saveCustomerType(CustomerTypeModel customerTypeModel);
}
