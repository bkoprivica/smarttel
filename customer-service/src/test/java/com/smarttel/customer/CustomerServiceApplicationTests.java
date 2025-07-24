package com.smarttel.customer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.cloud.stream.defaultBinder=test")
class CustomerServiceApplicationTests {
    @Test
    void contextLoads() {}
}
