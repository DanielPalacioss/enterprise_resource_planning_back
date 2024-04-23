package com.erp.salesmanagement.service.product;

import com.erp.salesmanagement.error.exceptions.RequestException;
import com.erp.salesmanagement.model.product.ProductModel;
import com.erp.salesmanagement.model.product.ProductStatusModel;
import com.erp.salesmanagement.repository.product.ProductCategoryRepository;
import com.erp.salesmanagement.repository.product.ProductRepository;
import com.erp.salesmanagement.repository.product.ProductStatusRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImp implements ProductService{

    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImp.class);
    private final ProductRepository productRepository;
    private final ProductStatusRepository productStatusRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductModel> listOfAllProduct(String status) {
        logger.info("Start search for all products");
        List<ProductModel> productList = new ArrayList<ProductModel>();
        if(status.replaceAll(" ","").equalsIgnoreCase("OnStandBy")) {
            productList = productRepository.findAllByProductStatus_status("onstandby").orElseThrow(() -> new RequestException("La lista de productos en estado '"+status+"' está vacía","100-Continue"));
        }
        else if(status.replaceAll(" ","").equalsIgnoreCase("Available")) {
            productList = productRepository.findAllByProductStatus_status("available").orElseThrow(() -> new RequestException("La lista de productos en estado '"+status+"' está vacía","100-Continue"));
        }
        else if(status.replaceAll(" ","").equalsIgnoreCase("Outofstock")) {
            productList = productRepository.findAllByProductStatus_status("outofstock").orElseThrow(() -> new RequestException("La lista de productos en estado '"+status+"' está vacía","100-Continue"));
        }
        else if(status.replaceAll(" ","").equalsIgnoreCase("Deleted")) {
            productList = productRepository.findAllByProductStatus_status("deleted").orElseThrow(() -> new RequestException("La lista de productos en estado '"+status+"' está vacía","100-Continue"));;
        }
        else if(status.replaceAll(" ","").equalsIgnoreCase("All")) {
            productList = productRepository.findAll();
            if(productList.isEmpty()) throw new RequestException("La lista de productos está vacía","100-Continue");
        }
        else throw new RequestException("No existe el estado: '"+status+"' en la categoria de producto","100-Continue");
        return productList;
    }

    @Override
    public ProductModel listProductById(int productId) {
        logger.info("starting search by product number");
        return productRepository.findById(productId).orElseThrow(() -> new RequestException("Product not found with id " + productId,"404-Not Found"));
    }

    @Override
    public void updateProduct(ProductModel updateProduct) {
        ProductModel product = productRepository.findById(updateProduct.getProductNumber()).orElseThrow(() -> new RequestException("Product not found with id " + updateProduct.getProductNumber(),"404-Not Found"));
        product.setProductReference(updateProduct.getProductReference());
        product.setProductVat(updateProduct.getProductVat());
        if((product.getCostPrice() != updateProduct.getCostPrice()) || (product.getSalePrice() != updateProduct.getSalePrice())) product.setPriceUpdateDate(LocalDateTime.now());
        product.setCostPrice(updateProduct.getCostPrice());
        product.setDiscount(updateProduct.getDiscount());
        product.setSalePrice(updateProduct.getSalePrice());
        product.setProductCategoryModel(updateProduct.getProductCategoryModel());
        product.setUpdateDate(LocalDateTime.now());
        product.earnings();
        logger.info("Start the modification of product");
        productRepository.save(product);logger.info("Start the modification of product");
    }

    @Override
    public void deleteProduct(int productId) {
        ProductModel product = productRepository.findById(productId).orElseThrow(() -> new RequestException("Product not found with id " + productId,"404-Not Found"));
        ProductStatusModel productStatusModel = productStatusRepository.findByStatus("deleted").orElseThrow(()-> new RequestException("Status not found with status: deleted", "404-Not Found"));
        product.setProductStatus(productStatusModel);
        logger.info("Start deleting product");
        productRepository.save(product);
    }

    @Override
    public void saveProduct(ProductModel product) {
        if(productRepository.findById(product.getProductNumber()).isEmpty()) {
            if(productStatusRepository.count()<1) throw new RequestException("No product status created","404-Not Found");
            if(productCategoryRepository.count()<1) throw new RequestException("No product category created","404-Not Found");
            product.setCreationDate(LocalDateTime.now());
            product.setUpdateDate(null);
            product.setPriceUpdateDate(null);
            product.earnings();
            ProductStatusModel productStatusModel = productStatusRepository.findByStatus("onstandby").orElseThrow(() -> new RequestException("Status not found with status: deleted", "404-Not Found"));
            product.setProductStatus(productStatusModel);
            logger.info("Start the creation of product");
            productRepository.save(product);
        }
        else throw new RequestException("The product with id '" + product.getProductNumber() +"' is already in the database", "400-Bad Request");
    }
}