package com.salesmanagementplatform.product.service;

import com.salesmanagementplatform.product.model.ProductCategoryModel;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategoryModel> listOfAllProductCategory();

    ProductCategoryModel listProductCategoryById(Long productCategoryId);

    void updateProductCategory(ProductCategoryModel productCategoryModel);

    void deleteProductCategory(Long productCategoryId);

    void saveProductCategory(ProductCategoryModel productCategoryModel);
}
