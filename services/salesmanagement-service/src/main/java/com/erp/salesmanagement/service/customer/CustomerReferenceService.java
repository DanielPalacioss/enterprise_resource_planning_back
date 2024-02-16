package com.erp.salesmanagement.service.customer;

import com.erp.salesmanagement.model.customer.CustomerReferenceModel;

import java.util.List;

public interface CustomerReferenceService {
    List<CustomerReferenceModel> listOfAllCustomersReference(String status);

    void updateCustomerReference(CustomerReferenceModel updatedCustomerReference);

    void deleteCustomerReference(Long customerReferenceId);

    void saveCustomerReference(CustomerReferenceModel customerReferenceModel);
}
