package com.smarttel.apigateway.collaboration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ApiGatewayCollaborationTest {

    private static WireMockServer customerService = new WireMockServer(WireMockConfiguration.options().dynamicPort());
    private static WireMockServer billingService = new WireMockServer(WireMockConfiguration.options().dynamicPort());
    private static WireMockServer notificationService = new WireMockServer(WireMockConfiguration.options().dynamicPort());

    @BeforeAll
    static void startServers() {
        customerService.start();
        billingService.start();
        notificationService.start();
    }

    @AfterAll
    static void stopServers() {
        customerService.stop();
        billingService.stop();
        notificationService.stop();
    }

    @DynamicPropertySource
    static void registerGatewayProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.gateway.routes[0].id", () -> "customer-service");
        registry.add("spring.cloud.gateway.routes[0].uri", customerService::baseUrl);
        registry.add("spring.cloud.gateway.routes[0].predicates[0]", () -> "Path=/customers/**");

        registry.add("spring.cloud.gateway.routes[1].id", () -> "billing-service");
        registry.add("spring.cloud.gateway.routes[1].uri", billingService::baseUrl);
        registry.add("spring.cloud.gateway.routes[1].predicates[0]", () -> "Path=/billings/**");

        registry.add("spring.cloud.gateway.routes[2].id", () -> "notification-service");
        registry.add("spring.cloud.gateway.routes[2].uri", notificationService::baseUrl);
        registry.add("spring.cloud.gateway.routes[2].predicates[0]", () -> "Path=/notifications/**");
    }

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void customerRouteForwardsCorrectly() {
        customerService.stubFor(get(urlEqualTo("/customers/42"))
                .willReturn(okJson("{\"id\":42,\"name\":\"Jane\"}")));

        webTestClient.get().uri("/customers/42")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(42)
                .jsonPath("$.name").isEqualTo("Jane");

        customerService.verify(getRequestedFor(urlEqualTo("/customers/42")));
    }

    @Test
    void billingRouteForwardsCorrectly() {
        billingService.stubFor(get(urlEqualTo("/billings/42"))
                .willReturn(okJson("{\"id\":42,\"amount\":99.99}")));

        webTestClient.get().uri("/billings/42")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(42)
                .jsonPath("$.amount").isEqualTo(99.99);

        billingService.verify(getRequestedFor(urlEqualTo("/billings/42")));
    }

    @Test
    void notificationRouteForwardsCorrectly() {
        notificationService.stubFor(post(urlEqualTo("/notifications"))
                .withRequestBody(equalToJson("{\"message\":\"hi\"}"))
                .willReturn(okJson("{\"status\":\"sent\"}")));

        webTestClient.post().uri("/notifications")
                .bodyValue("{\"message\":\"hi\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("sent");

        notificationService.verify(postRequestedFor(urlEqualTo("/notifications"))
                .withRequestBody(equalToJson("{\"message\":\"hi\"}")));
    }
}
