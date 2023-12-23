package com.salesmanagementplatform.orders.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.salesmanagementplatform.orders.error.DataValidation;
import com.salesmanagementplatform.orders.model.OrderModel;
import com.salesmanagementplatform.orders.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
@CrossOrigin(origins = "http://localhost:8082")
public class OrderController {
    @Autowired
    OrderService orderService;

    DataValidation dataValidation = new DataValidation();

    @GetMapping("list/{status}")
    public ResponseEntity<List<OrderModel>> getAllOrder(@PathVariable String status) {
        return ResponseEntity.ok(orderService.listAllOrder(status.replaceAll(" ","").toLowerCase()));
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderModel> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.listOrderById(orderId));
    }

    @PostMapping
    public ResponseEntity<?> saveOrder(@Valid @RequestBody OrderModel orderModel, BindingResult bindingResult){
        dataValidation.handleValidationError(bindingResult);
        orderService.saveOrder(orderModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The order has been created successfully.");
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<?> updateOrderStatus(@RequestParam Long orderId, @RequestParam String status)
    {
        orderService.updateOrderStatus(orderId,status.replaceAll(" ","").toLowerCase());
        return ResponseEntity.status(HttpStatus.CREATED).body("Order status updated successfully.");
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@Valid @RequestBody OrderModel orderModel, BindingResult bindingResult){
        dataValidation.handleValidationError(bindingResult);
        orderService.updateOrder(orderModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("The order has been successfully modified.");
    }
}
