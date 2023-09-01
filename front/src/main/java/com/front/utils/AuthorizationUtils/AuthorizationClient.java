package com.front.utils.AuthorizationUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Slf4j
public class AuthorizationClient {
    @Value("${keycloak.url}")
    private String keycloakUrl;

    @Value("${keycloak.client.id}")
    private String keycloakClientId;

    @Value("${keycloak.client.secret}")
    private String keycloakClientSecret;

    @Value("${keycloak.username}")
    private String keycloakUsername;

    @Value("${keycloak.password}")
    private String keycloakPassword;

    @Value("${keycloak.grant.type}")
    private String keycloakGrantType;

    @Autowired
    private RestTemplate restTemplate;
    private AuthorizationToken authorizationToken;


    public String getAccessToken() {
        if (authorizationToken != null && authorizationToken.getExpiresInAccessToken().isBefore(Instant.now())) { //токен аксцес просрочен
            return authorizationToken.getAccessToken();
        } else if (authorizationToken != null && authorizationToken.getExpiresInRefreshToken().isAfter(Instant.now())) {
            refreshToken();
            return authorizationToken.getAccessToken();
        }
        generateNewAccessToken();
        return authorizationToken.getAccessToken();
    }

    private void refreshToken() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("client_id", keycloakClientId);
            body.add("refresh_token", authorizationToken.getRefreshToken());
            body.add("grant_type", "refresh_token");
            body.add("client_secret", keycloakClientSecret);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(keycloakUrl, HttpMethod.POST, requestEntity, String.class);

            String responseBody = responseEntity.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            authorizationToken = objectMapper.readValue(responseBody, AuthorizationToken.class);
            authorizationToken.setExpiresInAccessToken(Instant.now().plusSeconds(290));
            authorizationToken.setExpiresInRefreshToken(Instant.now().plusSeconds(1700));
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage());
        } catch (HttpClientErrorException | JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostConstruct
    private void generateNewAccessToken() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("client_id", keycloakClientId);
            body.add("client_secret", keycloakClientSecret);
            body.add("username", keycloakUsername);
            body.add("password", keycloakPassword);
            body.add("grant_type", keycloakGrantType);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(keycloakUrl, HttpMethod.POST, requestEntity, String.class);

            String responseBody = responseEntity.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            authorizationToken = objectMapper.readValue(responseBody, AuthorizationToken.class);
            authorizationToken.setExpiresInAccessToken(Instant.now().plusSeconds(290));
            authorizationToken.setExpiresInRefreshToken(Instant.now().plusSeconds(1700));
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage());
        } catch (HttpClientErrorException | JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
