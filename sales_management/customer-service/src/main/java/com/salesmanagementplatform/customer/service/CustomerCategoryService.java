package com.salesmanagementplatform.customer.service;

import com.salesmanagementplatform.customer.model.CustomerCategoryModel;


import java.util.List;

public interface CustomerCategoryService {
    List<CustomerCategoryModel> listOfAllCustomersCategory();

    void updateCustomerCategory(CustomerCategoryModel customerCategoryModel);

    void deleteCustomerCategory(Long customerCategoryId);

    void saveCustomerCategory(CustomerCategoryModel customerCategoryModel);
}
