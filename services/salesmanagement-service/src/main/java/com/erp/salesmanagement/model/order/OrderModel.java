package com.erp.salesmanagement.model.order;

import com.erp.salesmanagement.error.exceptions.RequestException;
import com.erp.salesmanagement.model.customer.CustomerModel;
import com.erp.salesmanagement.model.product.ProductModel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
    private List<OrderDetails> orderDetailsList;

    @Transient
    private List<ProductModel> productList;

    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        for(OrderDetails orderDetails : orderDetailsList)
        {
            if (getSubTotal() == null && getTotal() == null) {
                setSubTotal(orderDetails.getSubtotal());
                setTotal(orderDetails.getTotal());
            } else {
                setSubTotal(getSubTotal() + orderDetails.getSubtotal());
                setTotal(getTotal() + orderDetails.getTotal());
            }
        }
        this.orderDetailsList = orderDetailsList;
    }

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
            setProductList(jsonNodeToList(productsJson, ProductModel.class));
            // Valida de que un producto no aparezca mas de 1 vez en los productos comprados
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

   public <T> JsonNode convertClassListToJson(List<T> list) {
       ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.valueToTree(list);
    }

    private static <T> List<T> jsonNodeToList(JsonNode jsonNode, Class<T> valueType) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
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
