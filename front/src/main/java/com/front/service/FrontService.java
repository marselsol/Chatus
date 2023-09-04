package com.front.service;

import com.front.dto.MessageDto;
import com.front.entity.Message;
import com.front.entity.User;
import com.front.utils.AuthorizationUtils.AuthorizationClient;
import com.front.utils.mappers.MessageMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FrontService {
    private final List<User> users = new ArrayList<>();
    private final Map<UUID, String> userLoginMap = new HashMap<>();
    private final RestTemplate restTemplate;
    private String chatName;
    private final AuthorizationClient authorizationClient;

    public FrontService(RestTemplate restTemplate, AuthorizationClient authorizationClient) {
        this.restTemplate = restTemplate;
        this.authorizationClient = authorizationClient;
    }

    @Value("${base.url}")
    private String baseURL;

    @Value("${send.message.endpoint.v1}")
    private String sendMessageEndpointV1;

    @Value("${get.chat.name.endpoint.v1}")
    private String getChatNameEndpointV1;

    @PostConstruct
    public void createUsers() {
        User user1 = new User(UUID.fromString("47377fcb-3d94-4af2-9707-7593a20f6f7a"), "user1", "user1email", "user1pass", "", LocalDateTime.now(), null);
        User user2 = new User(UUID.fromString("b7810996-b21f-42d2-9077-cae116f7773b"), "user2", "user2email", "user2pass", "", LocalDateTime.now(), null);
        users.add(user1);
        users.add(user2);
        for (User user : users) {
            userLoginMap.put(user.getId(), user.getLogin());
        }
        List<UUID> userIdList = users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
        chatName = getChatName(userIdList);
        log.info("chat_name: " + chatName);
    }

    public void createAndSendMessage(UUID fromUserId, UUID toUserId, Message message) {
        message.setCreated(new Date());
        message.setFromUserId(fromUserId);
        message.setMessageToId(toUserId);
        MessageDto messageDto = MessageMapper.INSTANCE.messageToMessageDto(message);
        sendMessage(messageDto);
    }

    private void sendMessage(MessageDto messageDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authorizationClient.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MessageDto> requestEntity = new HttpEntity<>(messageDto, headers);
        restTemplate.exchange(baseURL + sendMessageEndpointV1, HttpMethod.POST, requestEntity, String.class);
    }

    public List<Message> getMessages() {
        LocalDateTime startOfTime = LocalDateTime.of(2000, 1, 1, 0, 0);
        String apiUrl = UriComponentsBuilder.fromHttpUrl(baseURL)
                .pathSegment("message", "api", "messages", "v1", "read-message")
                .queryParam("chatName", chatName)
                .queryParam("dateTime", startOfTime.format(DateTimeFormatter.ISO_DATE_TIME))
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authorizationClient.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<Message>> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );
        return responseEntity.getBody();
    }

    public String getChatName(List<UUID> listUsersId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authorizationClient.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<UUID>> requestEntity = new HttpEntity<>(listUsersId, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                baseURL + getChatNameEndpointV1,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        return responseEntity.getBody();
    }

    public List<User> getUsers() {
        return users;
    }

    public Map<UUID, String> getUserLoginMap() {
        return userLoginMap;
    }
}
