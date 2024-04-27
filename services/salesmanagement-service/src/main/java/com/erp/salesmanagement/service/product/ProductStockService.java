package com.erp.salesmanagement.service.product;


import com.erp.salesmanagement.model.order.OrderDetails;
import com.erp.salesmanagement.model.product.ProductStockModel;

import java.util.List;


public interface ProductStockService {

    ProductStockModel listProductStockByProductId(int productNumber);

    void updateProductStock(ProductStockModel productStock);

    void reduceStock(List<OrderDetails> orderDetails);

    void cancellationOfStockReduction(List<OrderDetails> orderDetails);

    void saveProductStock(ProductStockModel productStock);
}
