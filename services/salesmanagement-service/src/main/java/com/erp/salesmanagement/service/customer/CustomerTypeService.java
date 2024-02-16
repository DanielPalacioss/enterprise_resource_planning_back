package com.erp.salesmanagement.service.customer;

import com.erp.salesmanagement.model.customer.CustomerTypeModel;

import java.util.List;

public interface CustomerTypeService {
    List<CustomerTypeModel> listOfAllCustomersType(String status);

    void updateCustomerType(CustomerTypeModel updatedCustomerType);

    void deleteCustomerType(Long customerTypeId);

    void saveCustomerType(CustomerTypeModel customerTypeModel);
}
