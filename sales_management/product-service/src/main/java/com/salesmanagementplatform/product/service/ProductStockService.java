package com.salesmanagementplatform.product.service;

import com.salesmanagementplatform.product.model.ProductModel;
import com.salesmanagementplatform.product.model.ProductStockModel;

import java.util.List;


public interface ProductStockService {

    ProductStockModel listProductStockByProductId(int productNumber);

    void updateProductStock(ProductStockModel productStock);

    void reduceStock(List<ProductModel> productList);

    void cancellationOfStockReduction(List<ProductModel> productList);

    void saveProductStock(ProductStockModel productStock);
}
