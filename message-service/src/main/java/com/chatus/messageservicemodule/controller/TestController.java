package com.chatus.messageservicemodule.controller;

import com.chatus.messageservicemodule.utils.AuthorizationUtils.AuthorizationClient;
import com.chatus.messageservicemodule.utils.DatabaseHealthMetric;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController

@Slf4j
public class TestController {

    private final RestTemplate restTemplate;
    private final Random random = new Random();
    private final AuthorizationClient authorizationClient;
    private final DatabaseHealthMetric databaseHealthMetric;

    public TestController(RestTemplate restTemplate, AuthorizationClient authorizationClient, DatabaseHealthMetric databaseHealthMetric) {
        this.restTemplate = restTemplate;
        this.authorizationClient = authorizationClient;
        this.databaseHealthMetric = databaseHealthMetric;
    }

    @GetMapping("/test-chat")
    @Timed("TimeTestChat") //Время выполнения
    public String testChat() {
        log.info("Starting method testChat");
        List<UUID> userIds = Arrays.asList(
                UUID.fromString("b7810996-b21f-42d2-9077-cae116f7773b"),
                UUID.fromString("b7810996-b21f-42d2-9077-cae116f7773b")
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authorizationClient.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8082/api/chat/v1/get-chat-name",
                HttpMethod.POST,
                new HttpEntity<>(userIds, headers),
                String.class
        );
        log.error(response.getBody());
        return "test";
    }

    @GetMapping("/test-log")
    @Timed("TimeTestLog")
    public String test() {
        log.info("Test logger info");
        log.error("Test logger error");
        databaseHealthMetric.isDatabaseUp();
        return "test";
    }

    @Scheduled(fixedRate = 15000)
    public void periodicallyExecute() {
        int randomDelay = (30 + random.nextInt(31)) * 1000;
        try {
            Thread.sleep(randomDelay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        callYourEndpoint();
    }

    private void callYourEndpoint() {
        String url = "http://localhost:8080/test-log";
        restTemplate.getForEntity(url, String.class);
    }
}
