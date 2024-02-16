package com.erp.salesmanagement.service.product;

import com.erp.salesmanagement.model.product.ProductModel;
import com.erp.salesmanagement.model.product.ProductStockModel;

import java.util.List;


public interface ProductStockService {

    ProductStockModel listProductStockByProductId(int productNumber);

    void updateProductStock(ProductStockModel productStock);

    void reduceStock(List<ProductModel> productList);

    void cancellationOfStockReduction(List<ProductModel> productList);

    void saveProductStock(ProductStockModel productStock);
}
