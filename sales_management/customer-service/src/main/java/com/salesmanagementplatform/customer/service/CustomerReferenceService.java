package com.salesmanagementplatform.customer.service;

import com.salesmanagementplatform.customer.model.CustomerReferenceModel;

import java.util.List;

public interface CustomerReferenceService {
    List<CustomerReferenceModel> listOfAllCustomersReference();

    void updateCustomerReference(CustomerReferenceModel updatedCustomerReference);

    void deleteCustomerReference(Long customerReferenceId);

    void saveCustomerReference(CustomerReferenceModel customerReferenceModel);
}
