package com.chatservice.controllers;

import com.chatservice.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequestMapping("/api/chat")
@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/v1/get-chat-name")
    public ResponseEntity<String> getChatName(@RequestBody List<UUID> userIds) {
        try {
            String chatId = chatService.getChatBetweenTwoUsers(userIds);
            return ResponseEntity.ok().body(chatId);
        } catch (Exception e) {
            log.error("Error getting chat name: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}