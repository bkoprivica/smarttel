package com.smarttel.notification;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.cloud.stream.defaultBinder=test")
class NotificationServiceApplicationTests {
    @Test
    void contextLoads() {}
}
