package com.erp.salesmanagement.service.order;

import com.erp.salesmanagement.error.exceptions.RequestException;
import com.erp.salesmanagement.model.order.OrderModel;
import com.erp.salesmanagement.model.order.OrderStatusModel;
import com.erp.salesmanagement.model.order.ProductDetails;
import com.erp.salesmanagement.repository.order.OrderRepository;
import com.erp.salesmanagement.repository.order.OrderStatusRepository;
import com.erp.salesmanagement.service.customer.CustomerService;
import com.erp.salesmanagement.service.product.ProductStockService;
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
    private CustomerService customerService;
    @Autowired
    private ProductStockService productStockService;

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
            customerService.listCustomerById(updatedOrder.getCustomer().getId());
            orderModel.setProductsJson(orderModel.convertStringToJsonNode(orderModel.getProducts()));
            orderModel.convertJsonToProductList();
            updatedOrder.convertJsonToProductList();
            List<ProductDetails> productDetailsList = new ArrayList<ProductDetails>();
            orderModel.setProductDetailsList(productDetailsList);
            updatedOrder.getProductList().forEach(product -> orderModel.addProductDetails(product));
            orderModel.convertDetailsListToJson(orderModel.getProductDetailsList());
            orderModel.setOrderDetails(orderModel.getOrderDetailsJson().toString());
            orderModel.convertProductsListToJson(updatedOrder.getProductList());
            orderModel.setUpdateDate(LocalDateTime.now());
            orderModel.setProducts(orderModel.getProductsJson().toString());
            orderModel.setCustomer(updatedOrder.getCustomer());
            orderModel.setShippingAddress(updatedOrder.getShippingAddress());
            orderModel.setExpirationDate(updatedOrder.getExpirationDate());
            productStockService.cancellationOfStockReduction(orderModel.getProductList());
            productStockService.reduceStock(updatedOrder.getProductList());
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
                    orderModel.setUpdateDate(LocalDateTime.now());
                    orderModel.setOrderStatus(orderStatus);
                    orderModel.setProductsJson(orderModel.convertStringToJsonNode(orderModel.getProducts()));
                    orderModel.convertJsonToProductList();
                    productStockService.reduceStock(orderModel.getProductList());
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
                productStockService.reduceStock(orderModel.getProductList());
                orderModel.setUpdateDate(LocalDateTime.now());
                orderModel.setOrderStatus(orderStatus);
                logger.info("Start the update of order status");
                orderRepository.save(orderModel);
            } else {
                orderModel.setUpdateDate(LocalDateTime.now());
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
            customerService.listCustomerById(orderModel.getCustomer().getId());
            orderModel.setCreationDate(LocalDateTime.now());
            orderModel.setUpdateDate(null);
            OrderStatusModel orderStatus = orderStatusRepository.findByStatus("preparing");
            if (orderStatus == null) {
                throw new RequestException("Order status not found with status preparing", "404-Not Found");
            }
            List<ProductDetails> productDetailsList = new ArrayList<ProductDetails>();
            orderModel.setOrderStatus(orderStatus);
            orderModel.setProducts(orderModel.getProductsJson().toString());
            orderModel.convertJsonToProductList();
            productStockService.reduceStock(orderModel.getProductList());
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
