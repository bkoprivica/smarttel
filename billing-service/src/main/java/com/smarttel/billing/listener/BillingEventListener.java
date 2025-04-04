package com.smarttel.billing.listener;

import com.smarttel.billing.service.BillingService;
import com.smarttel.billing.dto.CustomerDTO;  // Updated import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.function.Consumer;

@Component
public class BillingEventListener {

    @Autowired
    private BillingService billingService;

    @Bean
    public Consumer<CustomerDTO> customerCreated() {
        return customer -> {
            System.out.println("Received customer event for ID: " + customer.getId());
            billingService.createBilling(customer.getId());
        };
    }
}
