package com.erp.salesmanagement.service.product;

import com.erp.salesmanagement.model.product.ProductModel;

import java.util.List;

public interface ProductService {

    List<ProductModel> listOfAllProduct(String status);

    ProductModel listProductById(int productId);

    void updateProduct(ProductModel updateProduct);

    void deleteProduct(int productId);

    void saveProduct(ProductModel product);
}
