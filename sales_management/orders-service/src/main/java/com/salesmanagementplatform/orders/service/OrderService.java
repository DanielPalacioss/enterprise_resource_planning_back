package com.salesmanagementplatform.orders.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.salesmanagementplatform.orders.model.OrderModel;

import java.util.List;

public interface OrderService {

    List<OrderModel> listAllOrder(String orderStatus);

    OrderModel listOrderById(Long orderId);

    void updateOrder(OrderModel updatedOrder);

    void updateOrderStatus(Long orderId, String orderStatus);

    void saveOrder(OrderModel orderModel);
}
