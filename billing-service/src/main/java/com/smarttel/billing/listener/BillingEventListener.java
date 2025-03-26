package com.smarttel.billing.listener;

import com.smarttel.billing.service.BillingService;
import com.smarttel.customer.model.Customer; // Assuming event payload maps to this structure
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.function.Consumer;

@Component
public class BillingEventListener {

    @Autowired
    private BillingService billingService;

    @Bean
    public Consumer<Customer> customerCreated() {
        return customer -> {
            System.out.println("Received customer event for ID: " + customer.getId());
            billingService.createBilling(customer.getId());
        };
    }
}
