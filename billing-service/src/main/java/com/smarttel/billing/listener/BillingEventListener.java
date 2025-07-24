package com.smarttel.billing.listener;

import com.smarttel.billing.service.BillingService;
import com.smarttel.common.dto.CustomerDTO;  // Updated import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Consumer;

@Configuration
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
