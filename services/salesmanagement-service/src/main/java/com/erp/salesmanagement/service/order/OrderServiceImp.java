package com.erp.salesmanagement.service.order;

import com.erp.salesmanagement.error.exceptions.RequestException;
import com.erp.salesmanagement.model.order.OrderDetails;
import com.erp.salesmanagement.model.order.OrderModel;
import com.erp.salesmanagement.model.order.OrderStatusModel;
import com.erp.salesmanagement.repository.customer.CustomerRepository;
import com.erp.salesmanagement.repository.order.OrderDetailsRepository;
import com.erp.salesmanagement.repository.order.OrderRepository;
import com.erp.salesmanagement.repository.order.OrderStatusRepository;
import com.erp.salesmanagement.repository.product.ProductRepository;
import com.erp.salesmanagement.service.customer.CustomerService;
import com.erp.salesmanagement.service.product.ProductStockService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImp implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImp.class);
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final ProductStockService productStockService;
    private final ProductRepository productRepository;
    private final OrderDetailsRepository orderDetailsRepository;



    @Override
    public List<OrderModel> listAllOrder(String status) {
        logger.info("Start search for all orders");
        List<OrderModel> orderList = new ArrayList<OrderModel>();
        if (status.equalsIgnoreCase("active")) {
            orderList = orderRepository.findAllByPreparingAndOntheway().orElseThrow(() -> new RequestException("La lista de pedidos en estado '" + status + "' está vacía", "100-Continue"));
        } else if (status.equalsIgnoreCase("delivered")) {
            orderList = orderRepository.findAllByOrderStatus_status(status).orElseThrow(() -> new RequestException("La lista de pedidos en estado '" + status + "' está vacía", "100-Continue"));
        } else if (status.equalsIgnoreCase("canceled")) {
            orderList = orderRepository.findAllByOrderStatus_status(status).orElseThrow(() -> new RequestException("La lista de pedidos en estado '" + status + "' está vacía", "100-Continue"));
        } else throw new RequestException("No existe el estado: '" + status + "' en pedido", "100-Continue");
        return orderList;
    }

    @Override
    public OrderModel listOrderById(Long orderId) {
        logger.info("starting search by order id");
        return orderRepository.findById(orderId).orElseThrow(() -> new RequestException("Order not found with id " + orderId, "404-Not Found"));
    }

    @Override
    public void updateOrder(OrderModel updatedOrder) {
        OrderModel orderModel = orderRepository.findById(updatedOrder.getId()).orElseThrow(() -> new RequestException("Order not found with id " + updatedOrder.getId(), "404-Not Found"));
        if (!orderModel.getOrderStatus().getStatus().equalsIgnoreCase("canceled")) {
            List<OrderDetails> updateOrderDetails = getOrderDetailsList(updatedOrder.getOrderDetails());
            customerRepository.findById(updatedOrder.getCustomer().getId()).orElseThrow(() -> new RequestException("Customer not found with id " + updatedOrder.getCustomer().getId(),"404-Not Found"));
            orderModel.setUpdateDate(LocalDateTime.now());
            orderModel.setCustomer(updatedOrder.getCustomer());
            orderModel.setShippingAddress(updatedOrder.getShippingAddress());
            orderModel.setExpirationDate(updatedOrder.getExpirationDate());
            logger.info("Start the modification of order");
            productStockService.cancellationOfStockReduction(orderModel.getOrderDetails());
            try {   productStockService.reduceStock(updatedOrder.getOrderDetails());
            }catch (RequestException e)
            {
                productStockService.reduceStock(orderModel.getOrderDetails());
                throw e;
            }
            orderDetailsRepository.deleteAll(orderModel.getOrderDetails());
            orderModel.setOrderDetails(updateOrderDetails.stream().peek(orderDetail -> orderDetail.setOrderS(orderModel)).collect(Collectors.toList()));
            orderModel.setOrderDetailsList();
            orderDetailsRepository.saveAll(orderModel.getOrderDetails());
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
                productStockService.cancellationOfStockReduction(orderModel.getOrderDetails());
                logger.info("Start the update of order status");
                orderRepository.save(orderModel);
            } else
                throw new RequestException("The order status " + orderModel.getOrderStatus().getStatus() + " cannot be modified to " + status, "400-Bad Request");
        } else if (orderModel.getOrderStatus().getStatus().equals("canceled"))
            throw new RequestException("The order status " + orderModel.getOrderStatus().getStatus() + " cannot be modified", "400-Bad Request");
        else {
            OrderStatusModel orderStatus = orderStatusRepository.findByStatus(status).orElseThrow(() -> new RequestException("Order status not found with status " + status, "404-Not Found"));
            if (orderStatus.getStatus().equalsIgnoreCase("canceled")) {
                productStockService.cancellationOfStockReduction(orderModel.getOrderDetails());
            }
            orderModel.setUpdateDate(LocalDateTime.now());
            orderModel.setOrderStatus(orderStatus);
            logger.info("Start the update of order status");
            orderRepository.save(orderModel);
        }
    }

    @Override
    public void saveOrder(OrderModel orderModel) {
        if(customerRepository.count() < 1) throw new RequestException("No customer created","404-Not Found");
        if(orderStatusRepository.count() < 1) throw new RequestException("No order status created","404-Not Found");
        if (orderModel.getId()==null)
        {
            customerService.listCustomerById(orderModel.getCustomer().getId());
            orderModel.setOrderDetails(getOrderDetailsList(orderModel.getOrderDetails()));
            orderModel.setOrderDetailsList();
            orderModel.setCreationDate(LocalDateTime.now());
            orderModel.setUpdateDate(null);
            OrderStatusModel orderStatus = orderStatusRepository.findByStatus("preparing").orElseThrow(() -> new RequestException("Order status not found with status preparing", "404-Not Found"));
            orderModel.setOrderStatus(orderStatus);
            productStockService.reduceStock(orderModel.getOrderDetails());
            logger.info("Start the creation of order");
            OrderModel newOrder = orderRepository.save(orderModel);
            orderModel.getOrderDetails().forEach(orderDetail ->
            {
                orderDetail.setOrderS(newOrder);
                orderDetailsRepository.save(orderDetail);
            });
        }
        else throw new RequestException("The order id must be null", "400-Bad Request");
    }
    public List<OrderDetails> getOrderDetailsList(List<OrderDetails> orderDetails)
    {
        return orderDetails.stream().peek(orderDetail ->
        {
            int productNumber = orderDetail.getProduct().getProductNumber();
            AtomicInteger count= new AtomicInteger(0);
            orderDetails.forEach(orderDetails1 -> {
                if(orderDetails1.getProduct().getProductNumber() == productNumber)
                {
                    count.incrementAndGet();
                    if(count.get()>1) throw new RequestException("The product number "+productNumber +" is found more than once in the order.","400-Bad Request");
                }
            });
            double subtotalWithVat, subtotalWithDiscount;
            orderDetail.setSubtotal((double) (orderDetail.getProduct().getSalePrice() * orderDetail.getUnits()));
            subtotalWithVat = orderDetail.getSubtotal() + (orderDetail.getSubtotal() * orderDetail.getProduct().getProductVat()) / 100;
            subtotalWithDiscount = orderDetail.getSubtotal() - ((orderDetail.getSubtotal() * orderDetail.getProduct().getDiscount()) / 100);
            if (orderDetail.getProduct().getDiscount() > 0) {
                if (orderDetail.getProduct().getProductVat() > 0) orderDetail.setTotal(subtotalWithDiscount + ((subtotalWithDiscount * orderDetail.getProduct().getProductVat()) / 100));
                else orderDetail.setTotal(subtotalWithDiscount);
            } else if (orderDetail.getProduct().getProductVat() > 0) orderDetail.setTotal(subtotalWithVat);
            else orderDetail.setTotal(orderDetail.getSubtotal());
        }).collect(Collectors.toList());
    }
}
