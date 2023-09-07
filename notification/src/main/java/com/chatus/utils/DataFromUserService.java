package com.chatus.utils;

import com.chatus.utils.AuthorizationUtils.AuthorizationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class DataFromUserService {

    @Autowired
    private AuthorizationClient authorizationClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${base.url}")
    private String baseURL;

    @Value("${get.user.email.by.id.endpoint.v1}")
    private String getUserEmailByIdEndpointV1;


    public String getUsersEmail(UUID userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authorizationClient.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(userId.toString(), headers);
        String urlEndpoint = baseURL + getUserEmailByIdEndpointV1 + userId.toString();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                urlEndpoint,
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        return responseEntity.getBody();
    }
}
