package com.chatus.utils.AuthorizationUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

    private final RestTemplate restTemplate;
    private AuthorizationToken authorizationToken;

    public AuthorizationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String getAccessToken() {
        if (authorizationToken != null && authorizationToken.getExpiresInAccessToken().isAfter(Instant.now())) {
            return authorizationToken.getAccessToken();
        } else if (authorizationToken != null && authorizationToken.getExpiresInRefreshToken().isAfter(Instant.now())) {
            refreshToken();
            return authorizationToken.getAccessToken();
        }
        generateNewAccessToken();
        return authorizationToken.getAccessToken();
    }

    private AuthorizationToken requestAccessToken(String grantType, String username, String password) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("client_id", keycloakClientId);
            body.add("client_secret", keycloakClientSecret);
            body.add("grant_type", grantType);
            if ("password".equals(grantType)) {
                body.add("username", username);
                body.add("password", password);
            } else if ("refresh_token".equals(grantType)) {
                body.add("refresh_token", authorizationToken.getRefreshToken());
            }
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(keycloakUrl, HttpMethod.POST, requestEntity, String.class);

            String responseBody = responseEntity.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            AuthorizationToken token = objectMapper.readValue(responseBody, AuthorizationToken.class);
            token.setExpiresInAccessToken(Instant.now().plusSeconds(290));
            token.setExpiresInRefreshToken(Instant.now().plusSeconds(1700));
            return token;
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage());
        } catch (HttpClientErrorException | JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void refreshToken() {
        authorizationToken = requestAccessToken("refresh_token", null, null);
        log.info("A new REFRESH token has been generated: " + authorizationToken);
    }

    private void generateNewAccessToken() {
        authorizationToken = requestAccessToken(keycloakGrantType, keycloakUsername, keycloakPassword);
        log.info("A new token has been generated: " + authorizationToken);
    }
}
