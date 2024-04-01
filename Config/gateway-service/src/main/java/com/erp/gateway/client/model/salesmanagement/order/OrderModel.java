package com.erp.gateway.client.model.salesmanagement.order;

import com.erp.gateway.client.model.salesmanagement.customer.CustomerModel;
import com.erp.gateway.client.model.salesmanagement.product.ProductModel;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderModel {

    private Long id;

    private String shippingAddress;

    private LocalDateTime creationDate;

    private LocalDateTime expirationDate;

    private LocalDateTime updateDate;

    private String orderDetails;

    private String products;

    private Double subTotal;

    private Double total;

    private CustomerModel customer;

    private OrderStatusModel orderStatus;

    private JsonNode orderDetailsJson;

    private JsonNode productsJson;

    private List<OrderDetails> orderDetailsList;

    private List<ProductModel> productList;

}
