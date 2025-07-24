package com.smarttel.apigateway;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.DynamicPropertyRegistry;
import org.springframework.boot.test.util.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoutingIntegrationTest {

    private static MockWebServer customerServer = new MockWebServer();
    private static MockWebServer billingServer = new MockWebServer();
    private static MockWebServer notificationServer = new MockWebServer();

    @Autowired
    private WebTestClient webTestClient;

    @BeforeAll
    static void setup() throws IOException {
        customerServer.start();
        billingServer.start();
        notificationServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        customerServer.shutdown();
        billingServer.shutdown();
        notificationServer.shutdown();
    }

    @DynamicPropertySource
    static void gatewayProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.gateway.routes[0].uri", () -> customerServer.url("/").toString());
        registry.add("spring.cloud.gateway.routes[1].uri", () -> billingServer.url("/").toString());
        registry.add("spring.cloud.gateway.routes[2].uri", () -> notificationServer.url("/").toString());
    }

    @Test
    void customerRouteForwardsRequest() throws Exception {
        customerServer.enqueue(new MockResponse().setBody("customer ok"));

        webTestClient.get().uri("/customers/42").exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("customer ok");

        RecordedRequest req = customerServer.takeRequest();
        assertEquals("/customers/42", req.getPath());
    }

    @Test
    void billingRouteReturnsError() throws Exception {
        billingServer.enqueue(new MockResponse().setResponseCode(500).setBody("fail"));

        webTestClient.get().uri("/billings/1").exchange()
                .expectStatus().is5xxServerError()
                .expectBody(String.class).isEqualTo("fail");

        RecordedRequest req = billingServer.takeRequest();
        assertEquals("/billings/1", req.getPath());
    }

    @Test
    void unmatchedPathReturnsNotFound() {
        webTestClient.get().uri("/unknown").exchange()
                .expectStatus().isNotFound();
    }
}
