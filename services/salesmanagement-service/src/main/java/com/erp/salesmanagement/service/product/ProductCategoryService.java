package com.erp.salesmanagement.service.product;

import com.erp.salesmanagement.model.product.ProductCategoryModel;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategoryModel> listOfAllProductCategory(String status);

    ProductCategoryModel listProductCategoryById(Long productCategoryId);

    void updateProductCategory(ProductCategoryModel productCategoryModel);

    void deleteProductCategory(Long productCategoryId);

    void saveProductCategory(ProductCategoryModel productCategoryModel);
}
