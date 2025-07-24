package com.smarttel.customer.service;

import com.smarttel.customer.model.Customer;
import com.smarttel.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final StreamBridge streamBridge;

    @Autowired
    public CustomerService(CustomerRepository repository, StreamBridge streamBridge) {
        this.repository = repository;
        this.streamBridge = streamBridge;
    }

    public Customer createCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("customer must not be null");
        }
        Customer saved = repository.save(customer);
        streamBridge.send("customerCreated-out-0", saved);
        return saved;
    }

    public Optional<Customer> getCustomer(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return repository.findById(id);
    }
}
