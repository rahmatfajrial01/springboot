package com.backend.aji.service;

import com.backend.aji.dto.CustomerDTO;
import com.backend.aji.entity.Customer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Customer> getAllCustomers() {
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
        return query.getResultList();
    }

    public Optional<Customer> getCustomerById(Long id) {
        Customer customer = entityManager.find(Customer.class, id);
        return Optional.ofNullable(customer);
    }

    private boolean isEmailDuplicate(String email) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(c) FROM Customer c WHERE c.email = :email", Long.class);
        query.setParameter("email", email);
        return query.getSingleResult() > 0;
    }

    @Transactional
    public Customer createCustomer(CustomerDTO customerDTO) {
        // Check for duplicate email before creating
        if (isEmailDuplicate(customerDTO.getEmail())) {
            throw new IllegalArgumentException("A customer with the same email already exists.");
        }

        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        entityManager.persist(customer);
        return customer;
    }

    @Transactional
    public Customer updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = entityManager.find(Customer.class, id);
        if (customer != null) {
            if (isEmailDuplicate(customerDTO.getEmail())) {
                throw new IllegalArgumentException("A customer with the same email already exists.");
            }
            customer.setName(customerDTO.getName());
            customer.setEmail(customerDTO.getEmail());
            entityManager.merge(customer);
        }
        return customer;
    }

    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = entityManager.find(Customer.class, id);
        if (customer != null) {
            entityManager.remove(customer);
        }
    }
}
