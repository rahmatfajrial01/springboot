package com.backend.aji.service;

import com.backend.aji.response.ApiResponse;
import com.backend.aji.dto.OrderDTO;
import com.backend.aji.entity.Customer;
import com.backend.aji.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.backend.aji.response.CustomerOrdersResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Order> getAllOrders() {
        TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o", Order.class);
        return query.getResultList();
    }

    public Optional<Order> getOrderById(Long id) {
        Order order = entityManager.find(Order.class, id);
        return Optional.ofNullable(order);
    }

    public CustomerOrdersResponse getOrdersByCustomerId(Long customerId) {
        validateCustomer(customerId);

        TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o WHERE o.customer.id = :customerId", Order.class);
        query.setParameter("customerId", customerId);
        List<Order> orders = query.getResultList();

        // Calculate total amount
        Double totalAmount = orders.stream()
                .map(Order::getAmount)
                .reduce(0.0, Double::sum);

        List<CustomerOrdersResponse.OrderSummary> orderSummaries = orders.stream()
                .map(order -> new CustomerOrdersResponse.OrderSummary(
                        order.getId(),
                        order.getOrderDate().toString(),
                        order.getAmount()))
                .collect(Collectors.toList());

        return new CustomerOrdersResponse(customerId, orderSummaries, totalAmount);
    }

    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        // Validasi customer berdasarkan ID
        validateCustomer(orderDTO.getCustomerId());

        Order order = new Order();
        order.setOrderDate(orderDTO.getOrderDate());
        order.setAmount(orderDTO.getAmount());
        order.setCustomer(entityManager.find(Customer.class, orderDTO.getCustomerId())); // Set customer

        entityManager.persist(order);
        return order;
    }

    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDTO) {
        Order order = entityManager.find(Order.class, id);
        if (order != null) {
            order.setOrderDate(orderDTO.getOrderDate());
            order.setAmount(orderDTO.getAmount());
            order.setCustomer(entityManager.find(Customer.class, orderDTO.getCustomerId()));
            entityManager.merge(order);
        }
        return order;
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = entityManager.find(Order.class, id);
        if (order != null) {
            entityManager.remove(order);
        }
    }

    private void validateCustomer(Long customerId) {
        if (customerId == null || entityManager.find(Customer.class, customerId) == null) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist.");
        }
    }
}
