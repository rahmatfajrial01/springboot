package com.backend.aji.response;

import java.util.List;

public class CustomerOrdersResponse {
    private Long customerId;
    private List<OrderSummary> orders;
    private Double totalAmount; // New field for total amount

    public CustomerOrdersResponse(Long customerId, List<OrderSummary> orders, Double totalAmount) {
        this.customerId = customerId;
        this.orders = orders;
        this.totalAmount = totalAmount;
    }

    public Long getCustomerId() { return customerId; }
    public List<OrderSummary> getOrders() { return orders; }
    public Double getTotalAmount() { return totalAmount; } // Getter for total amount

    public static class OrderSummary {
        private Long id;
        private String orderDate;
        private Double amount;

        public OrderSummary(Long id, String orderDate, Double amount) {
            this.id = id;
            this.orderDate = orderDate;
            this.amount = amount;
        }

        public Long getId() { return id; }
        public String getOrderDate() { return orderDate; }
        public Double getAmount() { return amount; }
    }
}