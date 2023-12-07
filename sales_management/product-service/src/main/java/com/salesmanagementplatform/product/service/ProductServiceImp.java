package com.salesmanagementplatform.product.service;

import com.salesmanagementplatform.product.error.exceptions.RequestException;
import com.salesmanagementplatform.product.model.ProductModel;
import com.salesmanagementplatform.product.model.ProductStatusModel;
import com.salesmanagementplatform.product.repository.ProductRepository;
import com.salesmanagementplatform.product.repository.ProductStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService{

    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImp.class);

    private final ProductRepository productRepository;
    private final ProductStatusRepository productStatusRepository;

    public ProductServiceImp(ProductRepository productRepository, ProductStatusRepository productStatusRepository) {
        this.productRepository = productRepository;
        this.productStatusRepository = productStatusRepository;
    }

    @Override
    public List<ProductModel> listOfAllProduct() {
        logger.info("Start search for all products");
        List<ProductModel> productList = productRepository.findAll();
        if(productList.isEmpty()) throw new RequestException("La lista de productos está vacía","buscar cod");
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
        if(updateProduct.getDiscount() > 0)
        {
            float earnings = ((updateProduct.getSalePrice()*updateProduct.getDiscount())/100)-updateProduct.getCostPrice();
            product.setEarnings(earnings);
        }
        else {
            product.setEarnings(updateProduct.getSalePrice()-updateProduct.getCostPrice());
        }
        if(product.getCostPrice() != updateProduct.getCostPrice()) product.setPriceUpdateDate(LocalDateTime.now());
        logger.info("Start the modification of product");
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(int productId) {
        ProductModel product = productRepository.findById(productId).orElseThrow(() -> new RequestException("Product not found with id " + productId,"404-Not Found"));
        ProductStatusModel productStatusModel = productStatusRepository.finByStatus("deleted");
        if(productStatusModel == null) throw new RequestException("Status not found with id false", "404-Not Found");
        product.setProductStatus(productStatusModel);
        productRepository.save(product);
    }

    @Override
    public void saveProduct(ProductModel product) {

    }
}
