package com.salesmanagementplatform.product.service;

import com.salesmanagementplatform.product.model.ProductStatus;

import java.util.List;

public interface ProductStatusService {

    List<ProductStatus> listOfAllProductStatus();

    void updateProductStatus(ProductStatus productStatus);

    void deleteProductStatus(Long productStatusId);

    void saveProductStatus(ProductStatus productStatus);
}
