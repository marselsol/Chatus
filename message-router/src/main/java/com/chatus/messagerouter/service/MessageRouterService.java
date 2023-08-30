package com.chatus.messagerouter.service;

import com.chatus.messagerouter.entity.Message;
import com.chatus.messagerouter.utils.kafka.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class MessageRouterService {

    private final Producer producer;
    private final ObjectMapper objectMapper;
    private final ChatServiceInMessageRouter chatServiceInMessageRouter;

    public MessageRouterService(Producer producer, ObjectMapper objectMapper, ChatServiceInMessageRouter chatServiceInMessageRouter) {
        this.producer = producer;
        this.objectMapper = objectMapper;
        this.chatServiceInMessageRouter = chatServiceInMessageRouter;
    }

    @KafkaListener(topics = "incoming_messages", groupId = "group-id")
    public void listenMessageAndSendToChat(String message) {
        try {
            Message deserializedMessage = objectMapper.readValue(message, Message.class);
            List<UUID> userIds = Arrays.asList(deserializedMessage.getFromUserId(), deserializedMessage.getMessageToId());
            String chatName = chatServiceInMessageRouter.getChatId(userIds);
            producer.sendMessage(chatName, message);
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON message: {}", e.getMessage(), e);
            throw new RuntimeException("Error processing JSON message", e);
        } catch (Exception e) {
            log.error("An error occurred while processing the message: {}", e.getMessage(), e);
        }
    }
}
