package com.chatus.service;

import com.chatus.entity.Message;
import com.chatus.entity.UserNotificationPermissions;
import com.chatus.repository.NotificationPermissionsRepository;
import com.chatus.utils.DataFromUserService;
import com.chatus.utils.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class NotificationService {

    private final PermissionsService permissionsService;

    private final EmailService emailService;

    private final DataFromUserService dataFromUserService;

    public NotificationService(PermissionsService permissionsService, EmailService emailService, DataFromUserService dataFromUserService) {
        this.permissionsService = permissionsService;
        this.emailService = emailService;
        this.dataFromUserService = dataFromUserService;
    }

    @KafkaListener(topics = "notification", groupId = "notification-consumer")
    public void sendNotification(String jsonMessage) {
        Message message = serializeMessage(jsonMessage);
        UserNotificationPermissions userNotificationPermissions = permissionsService.getUserNotificationPermissions(message.getMessageToId());
        try {
            if (userNotificationPermissions.getAgreePushNotification()) {
                sendNotificationToPush(message);
            }
            if (userNotificationPermissions.getAgreeSmsNotification()) {
                sendNotificationToSms(message);
            }
            if (userNotificationPermissions.getAgreeEmailNotification()) {
                sendNotificationToEmail(message);
            }
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON message: {}", e.getMessage(), e);
            throw new RuntimeException("Error processing JSON message", e);
        } catch (Exception e) {
            log.error("An error occurred while processing the message: {}", e.getMessage(), e);
        }
    }

    private void sendNotificationToEmail(Message message) throws JsonProcessingException {
//        emailService.sendEmail(dataFromUserService.getUsersEmail(message.getMessageToId()), "Уведомление в Chatus", "Вам пришло письмо.");
        log.info("Пользователю с UUID " + message.getMessageToId() + " отправлено email-уведомление.");
    }

    private void sendNotificationToSms(Message message) {
        log.info("Пользователю с UUID " + message.getMessageToId() + " отправлено уведомление по SmS.");
    }

    private void sendNotificationToPush(Message message) {
        log.info("Пользователю с UUID " + message.getMessageToId() + " отправлено push-уведомление.");
    }

    public Message serializeMessage(String jsonMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        Message message;
        try {
            message = objectMapper.readValue(jsonMessage, Message.class);
        } catch (JsonProcessingException e) {
            log.info("Message object serialization error");
            throw new RuntimeException(e);
        }
        return message;
    }
}
