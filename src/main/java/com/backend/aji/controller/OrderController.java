package com.backend.aji.controller;

import com.backend.aji.response.ApiResponse;
import com.backend.aji.dto.OrderDTO;
import com.backend.aji.entity.Order;
import com.backend.aji.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.backend.aji.response.CustomerOrdersResponse;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Validated
@Tag(name = "Orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(new ApiResponse<>("Orders retrieved successfully.", orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(order -> ResponseEntity.ok(new ApiResponse<>("Order retrieved successfully.", order)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Order not found.", null)));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<CustomerOrdersResponse>> getOrdersByCustomerId(@PathVariable Long customerId) {
        CustomerOrdersResponse response = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(new ApiResponse<>("Orders retrieved successfully.", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Order createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Order created successfully.", createdOrder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) {
        Order updatedOrder = orderService.updateOrder(id, orderDTO);
        if (updatedOrder != null) {
            return ResponseEntity.ok(new ApiResponse<>("Order updated successfully.", updatedOrder));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Order not found.", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(new ApiResponse<>("Order deleted successfully.", null));
    }
}
