package com.smarttel.notification.listener;

import com.smarttel.customer.model.Customer; // Assuming shared event structure
import com.smarttel.notification.model.Notification;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.function.Consumer;

@Component
public class NotificationEventListener {

    @Bean
    public Consumer<Customer> customerCreatedNotification() {
        return customer -> {
            Notification notification = new Notification("Welcome, " + customer.getName() + "!");
            System.out.println("Notification sent: " + notification.getMessage());
        };
    }
}
