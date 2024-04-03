package com.erp.salesmanagement.service.order;

import com.erp.salesmanagement.model.order.OrderModel;

import java.util.List;

public interface OrderService {

    List<OrderModel> listAllOrder(String orderStatus);

    OrderModel listOrderById(Long orderId);

    void updateOrder(OrderModel updatedOrder);

    void updateOrderStatus(Long orderId, String orderStatus);

    void saveOrder(OrderModel orderModel);
}
