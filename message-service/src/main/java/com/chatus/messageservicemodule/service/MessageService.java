package com.chatus.messageservicemodule.service;

import com.chatus.messageservicemodule.dto.MessageDto;
import com.chatus.messageservicemodule.entity.Message;
import com.chatus.messageservicemodule.utils.kafka.ConsumerService;
import com.chatus.messageservicemodule.utils.kafka.Producer;
import com.chatus.messageservicemodule.utils.mappers.MessageMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class MessageService {

    private final Producer producer;
    private final ConsumerService consumerService;
    private final ObjectMapper objectMapper;

    public MessageService(Producer producer, ConsumerService consumerService, ObjectMapper objectMapper) {
        this.producer = producer;
        this.consumerService = consumerService;
        this.objectMapper = objectMapper;
    }

    public String sendMessage(MessageDto messageDto) {
        try {
            Message message = MessageMapper.INSTANCE.messageDtoToMessage(messageDto);
            message.setId(UUID.randomUUID());
            message.setCreated(new Date());
            String messageJson = objectMapper.writeValueAsString(message);
            //producer.sendMessage(messageJson, messageDto.getCreated().getTime());
            producer.sendMessage(messageJson);
            return messageJson;
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON while sending message: {}", e.getMessage(), e);
            throw new RuntimeException("Error processing JSON while sending message", e);
        } catch (Exception e) {
            log.error("An error occurred while sending message: {}", e.getMessage(), e);
            throw new RuntimeException("An error occurred while sending message: {}", e);
        }
    }

    public List<Message> readMessageFromDate(String chatName, LocalDateTime dateTime) {
        try {
            return consumerService.getMessagesFromTopicFromDate(chatName, dateTime);
        } catch (Exception e) {
            log.error("An error occurred while reading messages from topic {}: {}", chatName, e.getMessage(), e);
            throw new RuntimeException("An error occurred while reading the message: {}", e);
        }
    }
}