package com.salesmanagementplatform.product.service;

import com.salesmanagementplatform.product.model.ProductStockModel;

import java.util.List;

public interface ProductStockService {

    List<ProductStockModel> listOfAllProductStock();

    void updateProductStock(ProductStockModel productStock);

    void deleteProductStock(Long productStockId);

    void saveProductStock(ProductStockModel productStock);
}
