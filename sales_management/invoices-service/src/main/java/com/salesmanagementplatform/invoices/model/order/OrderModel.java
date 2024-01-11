package com.salesmanagementplatform.invoices.model.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.salesmanagementplatform.invoices.error.exceptions.RequestException;
import com.salesmanagementplatform.invoices.model.customer.CustomerModel;
import com.salesmanagementplatform.invoices.model.product.ProductModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Entity
@Table(name = "orderS")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 200, message = "Shipping address must be between 3 and 200 characters")
    @Column(name = "shippingAddress", length = 200, nullable = false)
    private String shippingAddress;

    @Column(name="creationDate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Future(message = "The date must be in the future.")
    @Column(name="expirationDate")
    private LocalDateTime expirationDate;

    @Column(name="updateDate")
    private LocalDateTime updateDate;

    @Column(name = "orderDetails",columnDefinition = "TEXT", nullable = false)
    private String orderDetails;

    @Column(name = "productList",columnDefinition = "TEXT", nullable = false)
    private String products;

    @DecimalMin(value = "1", message = "The minimum subTotal is 1")
    @Column(name = "subTotal", nullable = false)
    private Double subTotal;

    @DecimalMin(value = "1", message = "The minimum total is 1")
    @Column(name = "total", nullable = false)
    private Double total;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private CustomerModel customer;

    @ManyToOne
    @JoinColumn(name = "orderStatus", nullable = false)
    private OrderStatusModel orderStatus;

    @Transient
    private JsonNode orderDetailsJson;

    @Transient
    private JsonNode productsJson;

    @Transient
    private List<ProductDetails> productDetailsList;

    @Transient
    private List<ProductModel> productList;

    public JsonNode convertStringToJsonNode(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readTree(jsonString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void convertJsonToProductList() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            setProductList(jsonNodeToList(productsJson, ProductModel.class, objectMapper));
            getProductList().forEach(product ->
            {
                int productNumber =product.getProductNumber();
                AtomicInteger count = new AtomicInteger(0);
                getProductList().forEach(product2 -> {
                    if(product2.getProductNumber() == productNumber)
                    {
                        count.incrementAndGet();
                        if(count.get()>1)
                        {
                            throw new RequestException("The product number "+productNumber +" is found more than once in the order.","400-Bad Request");
                        }
                    }
                });
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void convertDetailsListToJson(List<ProductDetails> productDetailsList) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        setOrderDetailsJson(objectMapper.valueToTree(productDetailsList));
    }
    public void convertProductsListToJson(List<ProductModel> productsList) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        setProductsJson(objectMapper.valueToTree(productsList));
    }
    public void addProductDetails(ProductModel productModel)
    {
        Double subtotalWithVat, subtotalWithDiscount;
        ProductDetails productDetails= new ProductDetails();
        productDetails.setProductNumber(productModel.getProductNumber());
        productDetails.setDiscount(productModel.getDiscount());
        productDetails.setUnitPrice(productModel.getSalePrice());
        productDetails.setUnits(productModel.getQuantity());
        productDetails.setProductVat(productModel.getProductVat());
        productDetails.setProductReference(productModel.getProductReference());
        productDetails.setSubtotal((double) (productModel.getSalePrice() * productModel.getQuantity()));
        subtotalWithVat = productDetails.getSubtotal()+(productDetails.getSubtotal()*productModel.getProductVat())/100;
        subtotalWithDiscount = productDetails.getSubtotal()-((productDetails.getSubtotal()*productModel.getDiscount())/100);
        if(productModel.getDiscount()>0)
        {
            if(productModel.getProductVat()>0) productDetails.setTotal(subtotalWithDiscount+((subtotalWithDiscount*productModel.getProductVat())/100));

            else productDetails.setTotal(subtotalWithDiscount);
        }
        else if (productModel.getProductVat()>0) productDetails.setTotal(subtotalWithVat);
        else productDetails.setTotal(productDetails.getSubtotal());
        if (getSubTotal()==null && getTotal()==null)
        {
            setSubTotal(productDetails.getSubtotal());
            setTotal(productDetails.getTotal());
        }
        else
        {
            setSubTotal(getSubTotal()+productDetails.getSubtotal());
            setTotal(getTotal()+productDetails.getTotal());
        }
        productDetailsList.add(productDetails);
    }
    private static <T> List<T> jsonNodeToList(JsonNode jsonNode, Class<T> valueType, ObjectMapper objectMapper) throws IOException{
        if (jsonNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonNode;
            Iterator<JsonNode> elements = arrayNode.elements();
            List<T> resultList = new ArrayList<>();

            while (elements.hasNext()) {
                JsonNode element = elements.next();
                T item = objectMapper.treeToValue(element, valueType);
                resultList.add(item);
            }

            return resultList;
        }
        return null;
        }
}
