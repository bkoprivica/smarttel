package com.smarttel.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.cloud.stream.defaultBinder=test")
class ApiGatewayApplicationTests {
    @Test
    void contextLoads() {}
}
