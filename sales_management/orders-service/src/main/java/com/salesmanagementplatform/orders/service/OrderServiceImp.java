package com.salesmanagementplatform.orders.service;

import com.salesmanagementplatform.orders.client.CustomerClient;
import com.salesmanagementplatform.orders.client.ProductClient;
import com.salesmanagementplatform.orders.error.exceptions.RequestException;
import com.salesmanagementplatform.orders.model.OrderModel;
import com.salesmanagementplatform.orders.model.OrderStatusModel;
import com.salesmanagementplatform.orders.model.ProductDetails;
import com.salesmanagementplatform.orders.repository.OrderRepository;
import com.salesmanagementplatform.orders.repository.OrderStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImp implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImp.class);
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final CircuitBreakerFactory circuitBreakerFactory;
    @Autowired
    CustomerClient customerClient;
    @Autowired
    ProductClient productClient;

    public OrderServiceImp(OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, CircuitBreakerFactory circuitBreakerFactory) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @Override
    public List<OrderModel> listAllOrder(String status) {
        logger.info("Start search for all orders");
        List<OrderModel> orderList = new ArrayList<OrderModel>();
        if (status.equals("active")) {
            orderList = orderRepository.findAllByPreparingAndOntheway();
            if (orderList.isEmpty())
                throw new RequestException("La lista de pedidos en estado '" + status + "' está vacía", "100-Continue");
        } else if (status.equals("delivered")) {
            orderList = orderRepository.findAllByOrderStatus_status(status);
            if (orderList.isEmpty())
                throw new RequestException("La lista de pedidos en estado '" + status + "' está vacía", "100-Continue");
        } else if (status.equals("canceled")) {
            orderList = orderRepository.findAllByOrderStatus_status(status);
            if (orderList.isEmpty())
                throw new RequestException("La lista de pedidos en estado '" + status + "' está vacía", "100-Continue");
        } else throw new RequestException("No existe el estado: '" + status + "' en pedido", "100-Continue");
        orderList.forEach(orderModel ->
        {
            orderModel.setProductsJson(orderModel.convertStringToJsonNode(orderModel.getProducts()));
            orderModel.setOrderDetailsJson(orderModel.convertStringToJsonNode(orderModel.getOrderDetails()));
            orderModel.setProducts(null);
            orderModel.setOrderDetails(null);
        });
        return orderList;
    }

    @Override
    public OrderModel listOrderById(Long orderId) {
        logger.info("starting search by order id");
        OrderModel orderModel = orderRepository.findById(orderId).orElseThrow(() -> new RequestException("Order not found with id " + orderId, "404-Not Found"));
        orderModel.setProductsJson(orderModel.convertStringToJsonNode(orderModel.getProducts()));
        orderModel.setOrderDetailsJson(orderModel.convertStringToJsonNode(orderModel.getOrderDetails()));
        orderModel.setProducts(null);
        orderModel.setOrderDetails(null);
        return orderModel;
    }

    @Override
    public void updateOrder(OrderModel updatedOrder) {
        OrderModel orderModel = orderRepository.findById(updatedOrder.getId()).orElseThrow(() -> new RequestException("Order not found with id " + updatedOrder.getId(), "404-Not Found"));
        if (!orderModel.getOrderStatus().getStatus().equalsIgnoreCase("canceled")) {
            circuitBreakerFactory.create("getCustomerById").run(() -> customerClient.getCustomerById(updatedOrder.getCustomer().getId()),
                    throwable ->{
                        try {
                            throw throwable;
                        } catch (Throwable e) {
                            throw new RuntimeException(e);
                        }
                    });
            orderModel.setProductsJson(orderModel.convertStringToJsonNode(orderModel.getProducts()));
            orderModel.convertJsonToProductList();
            updatedOrder.convertJsonToProductList();
            List<ProductDetails> productDetailsList = new ArrayList<ProductDetails>();
            orderModel.setProductDetailsList(productDetailsList);
            updatedOrder.getProductList().forEach(product -> orderModel.addProductDetails(product));
            orderModel.convertDetailsListToJson(orderModel.getProductDetailsList());
            orderModel.setOrderDetails(orderModel.getOrderDetailsJson().toString());
            orderModel.convertProductsListToJson(updatedOrder.getProductList());
            orderModel.setOrderUpdateDate(LocalDateTime.now());
            orderModel.setProducts(orderModel.getProductsJson().toString());
            orderModel.setCustomer(updatedOrder.getCustomer());
            orderModel.setShippingAddress(updatedOrder.getShippingAddress());
            orderModel.setExpirationDate(updatedOrder.getExpirationDate());
            circuitBreakerFactory.create("reduceProductStock").run(() -> {
                        productClient.cancellationOfStockReduction(orderModel.getProductList());
                        return null;},
                    throwable ->{
                        try {
                            throw throwable;
                        } catch (Throwable e) {
                            throw new RuntimeException(e);
                        }
                    });
            circuitBreakerFactory.create("reduceProductStock").run(() -> {
                        productClient.reduceProductStock(updatedOrder.getProductList());
                        return null;},
                    throwable ->{
                        try {
                            throw throwable;
                        } catch (Throwable e) {
                            throw new RuntimeException(e);
                        }
                    });
            logger.info("Start the modification of order");
            orderRepository.save(orderModel);
        } else throw new RequestException("The order status " + orderModel.getOrderStatus().getStatus() + " cannot be modified ", "400-Bad Request");
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        OrderModel orderModel = orderRepository.findById(orderId).orElseThrow(() -> new RequestException("Order not found with id " + orderId, "404-Not Found"));
        if (orderModel.getOrderStatus().getStatus().equals("delivered")) {
            if (status.equals("canceled")) {
                OrderStatusModel orderStatus = orderStatusRepository.findByStatus(status);
                if (orderStatus == null) {
                    throw new RequestException("Order status not found with status " + status, "404-Not Found");
                } else {
                    orderModel.setOrderUpdateDate(LocalDateTime.now());
                    orderModel.setOrderStatus(orderStatus);
                    orderModel.setProductsJson(orderModel.convertStringToJsonNode(orderModel.getProducts()));
                    orderModel.convertJsonToProductList();
                    circuitBreakerFactory.create("reduceProductStock").run(() -> {
                                productClient.cancellationOfStockReduction(orderModel.getProductList());
                                return null;},
                            throwable ->{
                                try {
                                    throw throwable;
                                } catch (Throwable e) {
                                    throw new RuntimeException(e);
                                }
                            });
                    logger.info("Start the update of order status");
                    orderRepository.save(orderModel);
                }
            } else
                throw new RequestException("The order status " + orderModel.getOrderStatus().getStatus() + " cannot be modified to " + status, "400-Bad Request");
        } else if (orderModel.getOrderStatus().getStatus().equals("canceled"))
            throw new RequestException("The order status " + orderModel.getOrderStatus().getStatus() + " cannot be modified", "400-Bad Request");
        else {
            OrderStatusModel orderStatus = orderStatusRepository.findByStatus(status);
            if (orderStatus == null) {
                throw new RequestException("Order status not found with status " + status, "404-Not Found");
            } else if (orderStatus.getStatus().equalsIgnoreCase("canceled")) {
                orderModel.setProductsJson(orderModel.convertStringToJsonNode(orderModel.getProducts()));
                orderModel.convertJsonToProductList();
                circuitBreakerFactory.create("reduceProductStock").run(() -> {
                            productClient.cancellationOfStockReduction(orderModel.getProductList());
                            return null;},
                        throwable ->{
                            try {
                                throw throwable;
                            } catch (Throwable e) {
                                throw new RuntimeException(e);
                            }
                        });
                orderModel.setOrderUpdateDate(LocalDateTime.now());
                orderModel.setOrderStatus(orderStatus);
                logger.info("Start the update of order status");
                orderRepository.save(orderModel);
            } else {
                orderModel.setOrderUpdateDate(LocalDateTime.now());
                orderModel.setOrderStatus(orderStatus);
                logger.info("Start the update of order status");
                orderRepository.save(orderModel);
            }
        }

    }

    @Override
    public void saveOrder(OrderModel orderModel) {
        if (orderModel.getId()==null)
        {
            circuitBreakerFactory.create("getCustomerById").run(() -> customerClient.getCustomerById(orderModel.getCustomer().getId()),
                    throwable ->{
                        try {
                            throw throwable;
                        } catch (Throwable e) {
                            throw new RuntimeException(e);
                        }
                    });
            orderModel.setOrderDate(LocalDateTime.now());
            orderModel.setOrderUpdateDate(null);
            OrderStatusModel orderStatus = orderStatusRepository.findByStatus("preparing");
            if (orderStatus == null) {
                throw new RequestException("Order status not found with status preparing", "404-Not Found");
            }
            List<ProductDetails> productDetailsList = new ArrayList<ProductDetails>();
            orderModel.setOrderStatus(orderStatus);
            orderModel.setProducts(orderModel.getProductsJson().toString());
            orderModel.convertJsonToProductList();
            circuitBreakerFactory.create("reduceProductStock").run(() -> {
                productClient.reduceProductStock(orderModel.getProductList());
                        return null;},
                    throwable ->{
                        try {
                            throw throwable;
                        } catch (Throwable e) {
                            throw new RuntimeException(e);
                        }
                    });
            orderModel.setProductDetailsList(productDetailsList);
            orderModel.getProductList().forEach(product -> orderModel.addProductDetails(product));
            orderModel.convertDetailsListToJson(orderModel.getProductDetailsList());
            orderModel.setOrderDetails(orderModel.getOrderDetailsJson().toString());
            logger.info("Start the creation of order");
            orderRepository.save(orderModel);
        }
        else throw new RequestException("The order id must be null", "400-Bad Request");
    }
}
