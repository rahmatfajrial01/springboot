package com.backend.aji.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Order date is mandatory")
    @Future(message = "Order date must be in the future")
    private LocalDateTime orderDate;

    @NotNull(message = "Amount is mandatory")
    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    private Double amount;

    @NotNull(message = "Customer is mandatory")
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


    public Order() {
    }


    public Order(Long id, LocalDateTime orderDate, Double amount, Customer customer) {
        this.id = id;
        this.orderDate = orderDate;
        this.amount = amount;
        this.customer = customer;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}