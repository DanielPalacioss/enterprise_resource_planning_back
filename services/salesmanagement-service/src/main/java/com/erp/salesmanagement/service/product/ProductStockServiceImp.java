package com.erp.salesmanagement.service.product;

import com.erp.salesmanagement.error.exceptions.RequestException;
import com.erp.salesmanagement.model.product.ProductModel;
import com.erp.salesmanagement.model.product.ProductStatusModel;
import com.erp.salesmanagement.model.product.ProductStockModel;
import com.erp.salesmanagement.repository.product.ProductRepository;
import com.erp.salesmanagement.repository.product.ProductStatusRepository;
import com.erp.salesmanagement.repository.product.ProductStockRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductStockServiceImp implements ProductStockService{

    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImp.class);
    private final ProductStockRepository productStockRepository;
    private final ProductStatusRepository productStatusRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductStockModel listProductStockByProductId(int productNumber) {
        logger.info("starting search product stock by product number");
        ProductStockModel productStock= productStockRepository.findByProduct_productNumber(productNumber).orElseThrow(() -> new RequestException("ProductStock not found with product number " + productNumber,"404-Not Found"));
        return productStock;
    }

    @Override
    public void updateProductStock(ProductStockModel updateProductStock) {
        ProductStockModel productStock= productStockRepository.findByProduct_productNumber(updateProductStock.getProduct().getProductNumber()).orElseThrow(() -> new RequestException("ProductStock not found with product number " + updateProductStock.getProduct().getProductNumber(),"404-Not Found"));
        productStock.setUpdateDate(LocalDateTime.now());
        productStock.setQuantity(updateProductStock.getQuantity());
        saveProductStatus(productStock);
        logger.info("Start the modification of product stock");
        productStockRepository.save(productStock);
    }

    @Override
    public void reduceStock(List<ProductModel> productList) {
        List<ProductModel> products = new ArrayList<ProductModel>();
        List<ProductStockModel> productsStock = new ArrayList<ProductStockModel>();
        productList.forEach(product -> {
            if(product.getQuantity()<1) throw new RequestException("The minimum product quantity is 1","400-Bad Request");
            ProductStockModel productStock= productStockRepository.findByProduct_productNumber(product.getProductNumber()).orElseThrow(() -> new RequestException("ProductStock not found with product number " + product.getProductNumber(),"404-Not Found"));
            if(productStock.getQuantity() < product.getQuantity()) throw new RequestException("The product with product number " +product.getProductNumber()+ " only has " +productStock.getQuantity()+" units, the purchase must be equal to or less than said quantity.","400-Bad Request");
            productStock.setQuantity(productStock.getQuantity()-product.getQuantity());
            ProductStatusModel productStatusModel = productStatusRepository.findByStatus(productStock.updateStatusProduct()).orElseThrow(() -> new RequestException("Status not found with status: "+ productStock.updateStatusProduct(), "404-Not Found"));
            product.setProductStatus(productStatusModel);
            products.add(product);
            productsStock.add(productStock);
        });
        logger.info("Start reduced product");
        productRepository.saveAll(products);
        productStockRepository.saveAll(productsStock);
    }

    @Override
    public void cancellationOfStockReduction(List<ProductModel> productList) {
        productList.forEach(product -> {
            ProductStockModel productStock= productStockRepository.findByProduct_productNumber(product.getProductNumber()).orElseThrow(() -> new RequestException("ProductStock not found with product number " + product.getProductNumber(),"404-Not Found"));
            productStock.setQuantity(productStock.getQuantity()+product.getQuantity());
            ProductStatusModel productStatusModel = productStatusRepository.findByStatus(productStock.updateStatusProduct()).orElseThrow(() -> new RequestException("Status not found with status: "+ productStock.updateStatusProduct(), "404-Not Found"));
            product.setProductStatus(productStatusModel);
            productRepository.save(product);
            logger.info("Start cancellation of stock reduction");
            productStockRepository.save(productStock);
        });
    }

    @Override
    public void saveProductStock(ProductStockModel productStock) {
        if(productRepository.count() <1) throw new RequestException("No product created","404-Not Found");
        if((productStock.getId() == null) && (productStockRepository.findByProduct_productNumber(productStock.getProduct().getProductNumber()).get()==null))
        {
            productStock.setCreationDate(LocalDateTime.now());
            productStock.setUpdateDate(null);
            saveProductStatus(productStock);
            logger.info("Start the creation of stock");
            productStockRepository.save(productStock);
        }
        else throw new RequestException("The product stock id must be null and the product must not already have a stock created.", "400-Bad Request");
    }

    private void saveProductStatus(ProductStockModel productStock) {
        ProductModel product = productRepository.findById(productStock.getProduct().getProductNumber()).orElseThrow(() -> new RequestException("Product not found with id " + productStock.getProduct().getProductNumber(),"404-Not Found"));
        ProductStatusModel productStatusModel = productStatusRepository.findByStatus(productStock.updateStatusProduct()).orElseThrow(() -> new RequestException("Status not found with status: "+ productStock.updateStatusProduct(), "404-Not Found"));
        product.setProductStatus(productStatusModel);
        productRepository.save(product);
    }
}