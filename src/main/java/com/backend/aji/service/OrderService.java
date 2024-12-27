package com.backend.aji.service;

import com.backend.aji.entity.Order;
import com.backend.aji.repository.CustomerRepository;
import com.backend.aji.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Order createOrder(Order order) {

        if (order.getCustomer() != null && order.getCustomer().getId() != null) {
            Long customerId = order.getCustomer().getId();
            if (!customerRepository.existsById(customerId)) {
                throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist.");
            }
        } else {
            throw new IllegalArgumentException("Customer ID must be provided.");
        }

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setOrderDate(orderDetails.getOrderDate());
            order.setAmount(orderDetails.getAmount());
            order.setCustomer(orderDetails.getCustomer());
            return orderRepository.save(order);
        }
        return null;
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}