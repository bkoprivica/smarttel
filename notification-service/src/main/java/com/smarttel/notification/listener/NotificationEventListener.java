package com.smarttel.notification.listener;

import com.smarttel.common.dto.CustomerDTO;

import com.smarttel.notification.model.Notification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Consumer;

@Configuration
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
