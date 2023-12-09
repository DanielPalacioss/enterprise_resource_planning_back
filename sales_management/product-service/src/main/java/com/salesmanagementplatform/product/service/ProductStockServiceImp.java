package com.salesmanagementplatform.product.service;

import com.salesmanagementplatform.product.error.exceptions.RequestException;
import com.salesmanagementplatform.product.model.ProductModel;
import com.salesmanagementplatform.product.model.ProductStockModel;
import com.salesmanagementplatform.product.repository.ProductStockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductStockServiceImp implements ProductStockService{

    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImp.class);
    private final ProductStockRepository productStockRepository;

    public ProductStockServiceImp(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
    }

    @Override
    public ProductStockModel listProductStockByProductId(int productNumber) {
        ProductStockModel productStock= productStockRepository.findByProduct_productNumber(productNumber);
        if (productStock == null) throw new RequestException("ProductStock not found with product number " + productNumber,"404-Not Found");
        return productStock;
    }

    @Override
    public void updateProductStock(ProductStockModel updateProductStock) {
        ProductStockModel productStock = productStockRepository.findById(updateProductStock.getId()).orElseThrow(()-> new RequestException("Product stock not found with id " + updateProductStock.getId(),"404-Not Found"));
        productStock.setUpdateDate(LocalDateTime.now());
        productStock.setQuantity(updateProductStock.getQuantity());
        productStockRepository.save(productStock);
    }

    @Override
    public void reduceStock(List<ProductModel> productList) {
        productList.forEach(product -> {
            ProductStockModel productStock= productStockRepository.findByProduct_productNumber(product.getProductNumber());
            if (productStock == null) throw new RequestException("ProductStock not found with product number " + product.getProductNumber(),"404-Not Found");
            else if(productStock.getQuantity() < product.getQuantity()) throw new RequestException("The product with product number " +product.getProductNumber()+ " only has " +productStock.getQuantity()+" units, the purchase must be equal to or less than said quantity.","400-Bad Request");
            productStock.setQuantity(productStock.getQuantity()-product.getQuantity());
            productStockRepository.save(productStock);
        });
    }

    @Override
    public void saveProductStock(ProductStockModel productStock) {
        if((productStockRepository.findById(productStock.getId()).isEmpty()) && (productStockRepository.findByProduct_productNumber(productStock.getProduct().getProductNumber())==null))
        {
            productStock.setCreationDate(LocalDateTime.now());
            productStock.setUpdateDate(null);
            productStockRepository.save(productStock);
        }
        else {
            throw new RequestException("The productStock with product id '" + productStock.getProduct().getProductNumber() +"' OR stock id is already in the database", "400-Bad Request");
        }
    }
}