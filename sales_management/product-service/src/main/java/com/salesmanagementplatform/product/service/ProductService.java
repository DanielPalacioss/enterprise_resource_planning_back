package com.salesmanagementplatform.product.service;

import com.salesmanagementplatform.product.model.ProductModel;

import java.util.List;

public interface ProductService {

    List<ProductModel> listOfAllProduct();

    ProductModel listProductById(int productId);

    void updateProduct(ProductModel product);

    void deleteProduct(int productId);

    void saveProduct(ProductModel product);
}
