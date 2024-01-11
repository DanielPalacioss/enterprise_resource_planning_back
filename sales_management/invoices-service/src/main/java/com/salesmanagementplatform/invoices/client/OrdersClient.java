package com.salesmanagementplatform.invoices.client;

import com.salesmanagementplatform.invoices.model.order.OrderModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "orders-service", url = "http://localhost:8082/api/")
public interface OrdersClient {
    @PutMapping(value = "order/updateStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
    void updateOrderStatus(@RequestParam Long orderId, @RequestParam String status);

    @GetMapping(value = "order/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    OrderModel getOrderById(@PathVariable Long orderId);
}
