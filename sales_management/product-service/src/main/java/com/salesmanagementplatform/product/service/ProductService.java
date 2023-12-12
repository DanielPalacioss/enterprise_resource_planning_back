package com.salesmanagementplatform.product.service;

import com.salesmanagementplatform.product.model.ProductModel;

import java.util.List;

public interface ProductService {

    List<ProductModel> listOfAllProduct(String status);

    ProductModel listProductById(int productId);

    void updateProduct(ProductModel updateProduct);

    void deleteProduct(int productId);

    void saveProduct(ProductModel product);
}
