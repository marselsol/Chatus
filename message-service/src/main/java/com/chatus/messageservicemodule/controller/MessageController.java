package com.chatus.messageservicemodule.controller;

import com.chatus.messageservicemodule.dto.MessageDto;
import com.chatus.messageservicemodule.entity.Message;
import com.chatus.messageservicemodule.service.MessageService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/messages")
@Validated
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/v1/send-message")
    public ResponseEntity<?> sendMessage(@RequestBody @Valid MessageDto messageDto) {
        try {
            String message = messageService.sendMessage(messageDto);
            return ResponseEntity.ok().body(String.format("Message sent successfully:\n%s", message));
        } catch (Exception e) {
            log.error("Error sending message: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred while sending the message");
        }
    }

    @GetMapping("/v1/read-message")
    public ResponseEntity<?> readMessageFromDate(@RequestParam String chatName,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        try {
            List<Message> messages = messageService.readMessageFromDate(chatName, dateTime);
            return ResponseEntity.ok().body(messages);
        } catch (Exception e) {
            log.error("Error reading messages from date: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred while reading messages");
        }
    }

    @GetMapping("/test/{text}")
    public String test(@PathVariable String text) {
        log.info("Test logger info");
        log.error("Test logger error");
        log.error(text);
        return "test";
    }
}