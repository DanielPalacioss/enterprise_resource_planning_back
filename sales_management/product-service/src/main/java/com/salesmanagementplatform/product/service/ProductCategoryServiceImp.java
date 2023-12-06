package com.salesmanagementplatform.product.service;

import com.salesmanagementplatform.product.error.exceptions.RequestException;
import com.salesmanagementplatform.product.model.ProductCategoryModel;
import com.salesmanagementplatform.product.model.Status;
import com.salesmanagementplatform.product.repository.ProductCategoryRepository;
import com.salesmanagementplatform.product.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductCategoryServiceImp implements ProductCategoryService{

    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImp.class);
    private final ProductCategoryRepository productCategoryRepository;
    private final StatusRepository statusRepository;

    public ProductCategoryServiceImp(ProductCategoryRepository productCategoryRepository, StatusRepository statusRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public List<ProductCategoryModel> listOfAllProductCategory() {
        logger.info("Start search for all categories");
        List<ProductCategoryModel> productCategoryModelList = productCategoryRepository.findAll();
        if (productCategoryRepository.findAll().isEmpty()) throw new RequestException("La lista de categorias está vacía","buscar cod");
        return productCategoryModelList;
    }

    @Override
    public ProductCategoryModel listProductCategoryById(Long productCategoryId) {
        logger.info("starting search by category id");
        return productCategoryRepository.findById(productCategoryId).orElseThrow(() -> new RequestException("Product category not found with id " + productCategoryId,"404-Not Found"));
    }

    @Override
    public void updateProductCategory(ProductCategoryModel updateProductCategoryModel) {
        ProductCategoryModel productCategoryModel = productCategoryRepository.findById(updateProductCategoryModel.getId()).orElseThrow(() -> new RequestException("Product category not found with id " + updateProductCategoryModel.getId(),"404-Not Found"));
        productCategoryModel.setCategory(updateProductCategoryModel.getCategory());
        productCategoryModel.setDescription(updateProductCategoryModel.getDescription());
        productCategoryModel.setUpdateDate(LocalDateTime.now());
        logger.info("Start the modification of customer");
        productCategoryRepository.save(productCategoryModel);
    }

    @Override
    public void deleteProductCategory(Long productCategoryId) {
        ProductCategoryModel productCategoryModel = productCategoryRepository.findById(productCategoryId).orElseThrow(() -> new RequestException("Product category not found with id " + productCategoryId,"404-Not Found"));
        Status status = statusRepository.findById(false).orElseThrow(() -> new RequestException("Status not found with id false", "404-Not Found"));
        productCategoryModel.setStatus(status);
        productCategoryModel.setUpdateDate(LocalDateTime.now());
        logger.info("Start deleting customer");
        productCategoryRepository.save(productCategoryModel);
    }

    @Override
    public void saveProductCategory(ProductCategoryModel productCategoryModel) {
        if(productCategoryRepository.findById(productCategoryModel.getId()).isEmpty())
        {
            Status status = statusRepository.findById(true).orElseThrow(() -> new RequestException("Status not found with id true", "404-Not Found"));
            productCategoryModel.setStatus(status);
            productCategoryModel.setCreationDate(LocalDateTime.now());
            productCategoryModel.setUpdateDate(null);
            logger.info("Start the creation of product category");
            productCategoryRepository.save(productCategoryModel);
        }
        else {
            throw new RequestException("The product category with id '" + productCategoryModel.getId() +"' is already in the database", "400-Bad Request");
        }
    }
}
