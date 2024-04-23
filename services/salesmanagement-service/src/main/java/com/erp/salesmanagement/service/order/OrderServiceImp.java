package com.erp.salesmanagement.service.order;

import com.erp.salesmanagement.error.exceptions.RequestException;
import com.erp.salesmanagement.model.order.OrderDetails;
import com.erp.salesmanagement.model.order.OrderModel;
import com.erp.salesmanagement.model.order.OrderStatusModel;
import com.erp.salesmanagement.model.product.ProductModel;
import com.erp.salesmanagement.repository.customer.CustomerRepository;
import com.erp.salesmanagement.repository.order.OrderRepository;
import com.erp.salesmanagement.repository.order.OrderStatusRepository;
import com.erp.salesmanagement.service.customer.CustomerService;
import com.erp.salesmanagement.service.product.ProductStockService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImp implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImp.class);
    private final OrderRepository orderRepository;
    private final ProductModel productModel = new ProductModel();
    private final OrderStatusRepository orderStatusRepository;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final ProductStockService productStockService;



    @Override
    public List<OrderModel> listAllOrder(String status) {
        logger.info("Start search for all orders");
        List<OrderModel> orderList = new ArrayList<OrderModel>();
        if (status.equals("active")) {
            orderList = orderRepository.findAllByPreparingAndOntheway().orElseThrow(() -> new RequestException("La lista de pedidos en estado '" + status + "' está vacía", "100-Continue"));
        } else if (status.equals("delivered")) {
            orderList = orderRepository.findAllByOrderStatus_status(status).orElseThrow(() -> new RequestException("La lista de pedidos en estado '" + status + "' está vacía", "100-Continue"));
        } else if (status.equals("canceled")) {
            orderList = orderRepository.findAllByOrderStatus_status(status).orElseThrow(() -> new RequestException("La lista de pedidos en estado '" + status + "' está vacía", "100-Continue"));
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
            orderModel.setSubTotal(null);
            orderModel.setTotal(null);
            orderModel.setOrderDetailsList(productModel.productsListToOrderDetailsList(updatedOrder.getProductList()));
            orderModel.setOrderDetailsJson(orderModel.convertClassListToJson(orderModel.getOrderDetailsList()));
            orderModel.setOrderDetails(orderModel.getOrderDetailsJson().toString());
            orderModel.setProductsJson(orderModel.convertClassListToJson(updatedOrder.getProductList()));
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
                OrderStatusModel orderStatus = orderStatusRepository.findByStatus(status).orElseThrow(() -> new RequestException("Order status not found with status " + status, "404-Not Found"));
                orderModel.setUpdateDate(LocalDateTime.now());
                orderModel.setOrderStatus(orderStatus);
                orderModel.setProductsJson(orderModel.convertStringToJsonNode(orderModel.getProducts()));
                orderModel.convertJsonToProductList();
                productStockService.reduceStock(orderModel.getProductList());
                logger.info("Start the update of order status");
                orderRepository.save(orderModel);
            } else
                throw new RequestException("The order status " + orderModel.getOrderStatus().getStatus() + " cannot be modified to " + status, "400-Bad Request");
        } else if (orderModel.getOrderStatus().getStatus().equals("canceled"))
            throw new RequestException("The order status " + orderModel.getOrderStatus().getStatus() + " cannot be modified", "400-Bad Request");
        else {
            OrderStatusModel orderStatus = orderStatusRepository.findByStatus(status).orElseThrow(() -> new RequestException("Order status not found with status " + status, "404-Not Found"));
            if (orderStatus.getStatus().equalsIgnoreCase("canceled")) {
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
        if(customerRepository.count() < 1) throw new RequestException("No customer created","404-Not Found");
        if(orderStatusRepository.count() < 1) throw new RequestException("No order status created","404-Not Found");
        if (orderModel.getId()==null)
        {
            customerService.listCustomerById(orderModel.getCustomer().getId());
            orderModel.setCreationDate(LocalDateTime.now());
            orderModel.setUpdateDate(null);
            OrderStatusModel orderStatus = orderStatusRepository.findByStatus("preparing").orElseThrow(() -> new RequestException("Order status not found with status preparing", "404-Not Found"));
            List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
            orderModel.setOrderStatus(orderStatus);
            orderModel.setProducts(orderModel.getProductsJson().toString());
            orderModel.convertJsonToProductList();
            productStockService.reduceStock(orderModel.getProductList());
            orderModel.setOrderDetailsList(orderDetailsList);
            orderModel.setOrderDetailsList(productModel.productsListToOrderDetailsList(orderModel.getProductList()));
            orderModel.setOrderDetailsJson(orderModel.convertClassListToJson(orderModel.getOrderDetailsList()));
            orderModel.setOrderDetails(orderModel.getOrderDetailsJson().toString());
            logger.info("Start the creation of order");
            orderRepository.save(orderModel);
        }
        else throw new RequestException("The order id must be null", "400-Bad Request");
    }
}
