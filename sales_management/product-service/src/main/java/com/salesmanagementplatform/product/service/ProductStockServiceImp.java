package com.salesmanagementplatform.product.service;

import com.salesmanagementplatform.product.error.exceptions.RequestException;
import com.salesmanagementplatform.product.model.ProductStockModel;
import com.salesmanagementplatform.product.repository.ProductStockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public void reduceStock(int productStockId) {
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