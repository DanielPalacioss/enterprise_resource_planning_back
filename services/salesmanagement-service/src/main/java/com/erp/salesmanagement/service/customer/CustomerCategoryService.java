package com.erp.salesmanagement.service.customer;

import com.erp.salesmanagement.model.customer.CustomerCategoryModel;

import java.util.List;

public interface CustomerCategoryService {
    List<CustomerCategoryModel> listOfAllCustomersCategory(String status);

    void updateCustomerCategory(CustomerCategoryModel updatedCustomerCategory);

    void deleteCustomerCategory(Long customerCategoryId);

    void saveCustomerCategory(CustomerCategoryModel customerCategoryModel);
}
