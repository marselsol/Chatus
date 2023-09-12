package com.chatus.messageservicemodule.controller;

import com.chatus.messageservicemodule.utils.AuthorizationUtils.AuthorizationClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class TestController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AuthorizationClient authorizationClient;

    @GetMapping("/test-chat")
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
}
