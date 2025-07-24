package com.smarttel.notification.listener;

import com.smarttel.notification.dto.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationEventListenerTest {

    @Mock
    private CustomerDTO customer;

    @Test
    public void customerCreatedNotification_logsWelcomeMessage() {
        when(customer.getId()).thenReturn(123L);
        NotificationEventListener listener = new NotificationEventListener();
        Consumer<CustomerDTO> consumer = listener.customerCreatedNotification();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        try {
            consumer.accept(customer);
        } finally {
            System.setOut(originalOut);
        }

        verify(customer).getId();
        String output = outContent.toString().trim();
        assertEquals("Notification sent: Welcome, customer ID: 123", output);
    }
}
