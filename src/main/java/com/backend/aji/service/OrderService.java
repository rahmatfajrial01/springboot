package com.backend.aji.service;

import com.backend.aji.entity.Order;
import com.backend.aji.repository.CustomerRepository;
import com.backend.aji.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public List<Order> getAllOrders() {
        TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o", Order.class);
        return query.getResultList();
    }

    public Optional<Order> getOrderById(Long id) {
        Order order = entityManager.find(Order.class, id);
        return Optional.ofNullable(order);
    }

    @Transactional
    public Order createOrder(Order order) {
        if (order.getCustomer() != null && order.getCustomer().getId() != null) {
            Long customerId = order.getCustomer().getId();
            if (!customerRepository.existsById(customerId)) {
                throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist.");
            }
        } else {
            throw new IllegalArgumentException("Customer ID must be provided.");
        }

        entityManager.persist(order);
        return order;
    }

    @Transactional
    public Order updateOrder(Long id, Order orderDetails) {
        Order order = entityManager.find(Order.class, id);
        if (order != null) {
            order.setOrderDate(orderDetails.getOrderDate());
            order.setAmount(orderDetails.getAmount());
            order.setCustomer(orderDetails.getCustomer());
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
}
