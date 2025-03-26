package com.smarttel.notification.listener;

// Replace this:
// import com.smarttel.customer.model.Customer;
// With this:
import com.smarttel.notification.dto.CustomerDTO;

import com.smarttel.notification.model.Notification;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.function.Consumer;

@Component
public class NotificationEventListener {

    // Listen for customer creation events and send notifications
    @Bean
    public Consumer<CustomerDTO> customerCreatedNotification() {
        return customer -> {
            // In a real system, send an email/SMS. For now, just log.
            Notification notification = new Notification("Welcome, customer ID: " + customer.getId());
            System.out.println("Notification sent: " + notification.getMessage());
        };
    }
}
