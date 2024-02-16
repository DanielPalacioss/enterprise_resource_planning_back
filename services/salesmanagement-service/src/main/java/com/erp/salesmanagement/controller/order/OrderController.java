package com.erp.salesmanagement.controller.order;

import com.erp.salesmanagement.error.DataValidation;
import com.erp.salesmanagement.model.order.OrderModel;
import com.erp.salesmanagement.service.order.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
@CrossOrigin(origins = "http://localhost:8081")
public class OrderController {
    @Autowired
    OrderService orderService;

    DataValidation dataValidation = new DataValidation();

    @GetMapping("list/{status}")
    public ResponseEntity<List<OrderModel>> getAllOrder(@PathVariable String status) {
        return ResponseEntity.ok(orderService.listAllOrder(status));
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
