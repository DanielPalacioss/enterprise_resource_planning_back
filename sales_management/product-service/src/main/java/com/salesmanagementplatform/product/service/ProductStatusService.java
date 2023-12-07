package com.salesmanagementplatform.product.service;

import com.salesmanagementplatform.product.model.ProductStatusModel;

import java.util.List;

public interface ProductStatusService {

    List<ProductStatusModel> listOfAllProductStatus();

    void updateProductStatus(ProductStatusModel productStatus);

    void deleteProductStatus(Long productStatusId);

    void saveProductStatus(ProductStatusModel productStatus);
}
