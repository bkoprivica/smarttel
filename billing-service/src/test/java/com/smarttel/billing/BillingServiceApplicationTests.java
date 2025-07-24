package com.smarttel.billing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.cloud.stream.defaultBinder=test")
class BillingServiceApplicationTests {
    @Test
    void contextLoads() {}
}
