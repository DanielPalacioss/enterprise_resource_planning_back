package com.salesmanagementplatform.product.service;

import com.salesmanagementplatform.product.error.exceptions.RequestException;
import com.salesmanagementplatform.product.model.ProductModel;
import com.salesmanagementplatform.product.model.ProductStatusModel;
import com.salesmanagementplatform.product.model.ProductStockModel;
import com.salesmanagementplatform.product.repository.ProductRepository;
import com.salesmanagementplatform.product.repository.ProductStatusRepository;
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
    private final ProductStatusRepository productStatusRepository;
    private final ProductRepository productRepository;

    public ProductStockServiceImp(ProductStockRepository productStockRepository, ProductStatusRepository productStatusRepository, ProductRepository productRepository) {
        this.productStockRepository = productStockRepository;
        this.productStatusRepository = productStatusRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ProductStockModel listProductStockByProductId(int productNumber) {
        logger.info("starting search product stock by product number");
        ProductStockModel productStock= productStockRepository.findByProduct_productNumber(productNumber);
        if (productStock == null) throw new RequestException("ProductStock not found with product number " + productNumber,"404-Not Found");
        return productStock;
    }

    @Override
    public void updateProductStock(ProductStockModel updateProductStock) {
        ProductStockModel productStock= productStockRepository.findByProduct_productNumber(updateProductStock.getProduct().getProductNumber());
        if (productStock == null) throw new RequestException("ProductStock not found with product number " + updateProductStock.getProduct().getProductNumber(),"404-Not Found");
        productStock.setUpdateDate(LocalDateTime.now());
        productStock.setQuantity(updateProductStock.getQuantity());
        ProductModel product = productRepository.findById(productStock.getProduct().getProductNumber()).orElseThrow(() -> new RequestException("Product not found with id " + productStock.getProduct().getProductNumber(),"404-Not Found"));
        ProductStatusModel productStatusModel = productStatusRepository.findByStatus(productStock.updateStatusProduct());
        if(productStatusModel == null) throw new RequestException("Status not found with status: "+ productStock.updateStatusProduct(), "404-Not Found");
        product.setProductStatus(productStatusModel);
        productRepository.save(product);
        logger.info("Start the modification of product stock");
        productStockRepository.save(productStock);
    }

    @Override
    public void reduceStock(List<ProductModel> productList) {
        productList.forEach(product -> {
            ProductStockModel productStock= productStockRepository.findByProduct_productNumber(product.getProductNumber());
            if (productStock == null) throw new RequestException("ProductStock not found with product number " + product.getProductNumber(),"404-Not Found");
            else if(productStock.getQuantity() < product.getQuantity()) throw new RequestException("The product with product number " +product.getProductNumber()+ " only has " +productStock.getQuantity()+" units, the purchase must be equal to or less than said quantity.","400-Bad Request");
            productStock.setQuantity(productStock.getQuantity()-product.getQuantity());
            ProductStatusModel productStatusModel = productStatusRepository.findByStatus(productStock.updateStatusProduct());
            if(productStatusModel == null) throw new RequestException("Status not found with status: "+ productStock.updateStatusProduct(), "404-Not Found");
            product.setProductStatus(productStatusModel);
            productRepository.save(product);
            logger.info("Start reduced product");
            productStockRepository.save(productStock);
        });
    }

    @Override
    public void saveProductStock(ProductStockModel productStock) {
        if((productStock.getId() == null) && (productStockRepository.findByProduct_productNumber(productStock.getProduct().getProductNumber())==null))
        {
            productStock.setCreationDate(LocalDateTime.now());
            productStock.setUpdateDate(null);
            ProductModel product = productRepository.findById(productStock.getProduct().getProductNumber()).orElseThrow(() -> new RequestException("Product not found with id " + productStock.getProduct().getProductNumber(),"404-Not Found"));
            ProductStatusModel productStatusModel = productStatusRepository.findByStatus(productStock.updateStatusProduct());
            if(productStatusModel == null) throw new RequestException("Status not found with status: "+ productStock.updateStatusProduct(), "404-Not Found");
            product.setProductStatus(productStatusModel);
            productRepository.save(product);
            logger.info("Start the creation of stock");
            productStockRepository.save(productStock);
        }
        else {
            throw new RequestException("The product stock id must be null and the product must not already have a stock created.", "400-Bad Request");
        }
    }
}