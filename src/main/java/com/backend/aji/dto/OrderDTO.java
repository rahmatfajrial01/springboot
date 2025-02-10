package com.backend.aji.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class OrderDTO {

    @NotNull(message = "Order date is mandatory")
    private LocalDateTime orderDate = LocalDateTime.now();

    @NotNull(message = "productName is mandatory")
    private String productName;

    @NotNull(message = "Amount is mandatory")
    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    private Double amount;

    @NotNull(message = "Customer ID is mandatory")
    private Long customerId;

}
