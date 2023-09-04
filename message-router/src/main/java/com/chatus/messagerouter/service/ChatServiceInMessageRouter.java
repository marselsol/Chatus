package com.chatus.messagerouter.service;

import com.chatus.messagerouter.utils.AuthorizationUtils.AuthorizationClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ChatServiceInMessageRouter {

    private final RestTemplate restTemplate;

    public ChatServiceInMessageRouter(RestTemplate restTemplate, AuthorizationClient authorizationClient) {
        this.restTemplate = restTemplate;
        this.authorizationClient = authorizationClient;
    }

    private final AuthorizationClient authorizationClient;
    @Value("${chat-service.url}")
    private String chatServiceUrl;

    public String getChatId(List<UUID> userIds) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authorizationClient.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    chatServiceUrl,
                    HttpMethod.POST,
                    new HttpEntity<>(userIds, headers),
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                log.error("Received non-OK response from the chat service: {}", response.getStatusCodeValue());
                throw new RuntimeException("Error while getting chatName");
            }
        } catch (Exception e) {
            log.error("An error occurred while communicating with the chat service: {}", e.getMessage(), e);
            throw new RuntimeException("Error while getting chatName", e);
        }
    }
}
