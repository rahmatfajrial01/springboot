package com.backend.aji.response;

import java.util.List;

public class CustomerOrdersResponse {
    private Long customerId;
    private List<OrderSummary> orders;
    private Double totalAmount;


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
        private String productName;
        private Double amount;

        public OrderSummary(Long id, String orderDate, Double amount, String productName) {
            this.id = id;
            this.orderDate = orderDate;
            this.amount = amount;
            this.productName = productName;
        }

        public Long getId() { return id; }
        public String getOrderDate() { return orderDate; }
        public Double getAmount() { return amount; }
        public String getProductName() { return productName; }
    }
}