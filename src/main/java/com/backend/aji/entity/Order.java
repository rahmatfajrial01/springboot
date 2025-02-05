package com.backend.aji.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Product Name is mandatory")
    private String  productName;

    @NotNull(message = "Order date is mandatory")
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


}