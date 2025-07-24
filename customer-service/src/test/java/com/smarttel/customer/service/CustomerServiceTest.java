package com.smarttel.customer.service;

import com.smarttel.customer.model.Customer;
import com.smarttel.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.stream.function.StreamBridge;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private StreamBridge streamBridge;

    @InjectMocks
    private CustomerService service;

    @Test
    public void createCustomer_persistsAndPublishesEvent() {
        Customer input = new Customer("Jane", "jane@example.com");
        Customer saved = new Customer("Jane", "jane@example.com");
        when(repository.save(any(Customer.class))).thenReturn(saved);
        when(streamBridge.send(anyString(), any())).thenReturn(true);

        Customer result = service.createCustomer(input);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(repository).save(captor.capture());
        verify(streamBridge).send(eq("customerCreated-out-0"), eq(saved));

        assertEquals(input.getName(), captor.getValue().getName());
        assertEquals(input.getEmail(), captor.getValue().getEmail());
        assertSame(saved, result);
    }

    @Test
    public void createCustomer_nullCustomerThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.createCustomer(null));
    }

    @Test
    public void getCustomer_foundReturnsCustomer() {
        Customer customer = new Customer("Bob", "bob@example.com");
        when(repository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> result = service.getCustomer(1L);

        assertTrue(result.isPresent());
        assertSame(customer, result.get());
    }

    @Test
    public void getCustomer_notFoundReturnsEmpty() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<Customer> result = service.getCustomer(99L);

        assertFalse(result.isPresent());
    }

    @Test
    public void getCustomer_nullIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.getCustomer(null));
    }
}
