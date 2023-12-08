package com.salesmanagementplatform.product.service;

import com.salesmanagementplatform.product.error.exceptions.RequestException;
import com.salesmanagementplatform.product.model.ProductModel;
import com.salesmanagementplatform.product.model.ProductStatusModel;
import com.salesmanagementplatform.product.repository.ProductCategoryRepository;
import com.salesmanagementplatform.product.repository.ProductRepository;
import com.salesmanagementplatform.product.repository.ProductStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService{

    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImp.class);

    private final ProductRepository productRepository;
    private final ProductStatusRepository productStatusRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductServiceImp(ProductRepository productRepository, ProductStatusRepository productStatusRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productStatusRepository = productStatusRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public List<ProductModel> listOfAllProduct(String status) {
        logger.info("Start search for all products");
        List<ProductModel> productList = new ArrayList<ProductModel>();
        if(status.replaceAll(" ","").equalsIgnoreCase("OnStandBy")) productList = productRepository.findAllByProductStatus_status("onstandby");
        else if(status.replaceAll(" ","").equalsIgnoreCase("Available")) productList = productRepository.findAllByProductStatus_status("available");
        else if(status.replaceAll(" ","").equalsIgnoreCase("Outofstock")) productList = productRepository.findAllByProductStatus_status("outofstock");
        else if(status.replaceAll(" ","").equalsIgnoreCase("Deleted")) productList = productRepository.findAllByProductStatus_status("deleted");
        if(productList.isEmpty()) throw new RequestException("La lista de productos en estado '"+status+"' está vacía","100-Continue");
        return productList;
    }

    @Override
    public ProductModel listProductById(int productId) {
        logger.info("starting search by category id");
        return productRepository.findById(productId).orElseThrow(() -> new RequestException("Product not found with id " + productId,"404-Not Found"));
    }

    @Override
    public void updateProduct(ProductModel updateProduct) {
        ProductModel product = productRepository.findById(updateProduct.getProductNumber()).orElseThrow(() -> new RequestException("Product not found with id " + updateProduct.getProductNumber(),"404-Not Found"));
        product.setProductReference(updateProduct.getProductReference());
        product.setProductVat(updateProduct.getProductVat());
        product.setCostPrice(updateProduct.getCostPrice());
        product.setDiscount(updateProduct.getDiscount());
        product.setSalePrice(updateProduct.getSalePrice());
        product.setProductCategoryModel(updateProduct.getProductCategoryModel());
        product.setUpdateDate(LocalDateTime.now());
        product.earnings();
        if(product.getCostPrice() != updateProduct.getCostPrice()) product.setPriceUpdateDate(LocalDateTime.now());
        logger.info("Start the modification of product");
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(int productId) {
        ProductModel product = productRepository.findById(productId).orElseThrow(() -> new RequestException("Product not found with id " + productId,"404-Not Found"));
        ProductStatusModel productStatusModel = productStatusRepository.findByStatus("deleted");
        if(productStatusModel == null) throw new RequestException("Status not found with status: deleted", "404-Not Found");
        product.setProductStatus(productStatusModel);
        productRepository.save(product);
    }

    @Override
    public void saveProduct(ProductModel product) {
        if(productRepository.findById(product.getProductNumber()).isEmpty())
        {
            if(productStatusRepository.findAll().isEmpty()) throw new RequestException("No product status created","404-Bad Request");
            if(productCategoryRepository.findAll().isEmpty()) throw new RequestException("No product category created","404-Bad Request");
            product.setCreationDate(LocalDateTime.now());
            product.setUpdateDate(null);
            product.setPriceUpdateDate(null);
            product.earnings();
            ProductStatusModel productStatusModel = productStatusRepository.findByStatus("onstandby");
            if(productStatusModel == null) throw new RequestException("Status not found with status: deleted", "404-Not Found");
            product.setProductStatus(productStatusModel);
            logger.info("Start the creation of product");
            productRepository.save(product);
        }
        else {
            throw new RequestException("The product with id '" + product.getProductNumber() +"' is already in the database", "400-Bad Request");
        }

    }
}