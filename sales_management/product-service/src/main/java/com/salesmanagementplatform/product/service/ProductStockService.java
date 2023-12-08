package com.salesmanagementplatform.product.service;

import com.salesmanagementplatform.product.model.ProductStockModel;


public interface ProductStockService {

    ProductStockModel listProductStockByProductId(int productNumber);

    void updateProductStock(ProductStockModel productStock);

    void reduceStock(int productStockId);

    void saveProductStock(ProductStockModel productStock);
}
