package com.backend.aji.controller;

import com.backend.aji.response.ApiResponse;
import com.backend.aji.dto.CustomerDTO;
import com.backend.aji.entity.Customer;
import com.backend.aji.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Validated
@Tag(name = "Customer", description = "API for managing customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Customer>>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(new ApiResponse<>("Customers retrieved successfully.", customers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(customer -> ResponseEntity.ok(new ApiResponse<>("Customer retrieved successfully.", customer)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Customer not found.", null)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer createdCustomer = customerService.createCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Customer created successfully.", createdCustomer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDTO);
        if (updatedCustomer != null) {
            return ResponseEntity.ok(new ApiResponse<>("Customer updated successfully.", updatedCustomer));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Customer not found.", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(new ApiResponse<>("Customer deleted successfully.", null));
    }
}
