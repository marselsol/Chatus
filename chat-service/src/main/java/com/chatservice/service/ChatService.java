package com.chatservice.service;

import com.chatservice.entity.Chat;
import com.chatservice.repository.ChatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public String getChatBetweenTwoUsers(List<UUID> userIds) {
        try {
            List<UUID> uuids = new ArrayList<>(userIds);
            Collections.sort(uuids);
            Optional<Chat> chat = chatRepository.findChatByUserIdFirstAndUserIdSecond(uuids.get(0), uuids.get(1));
            if (chat.isPresent()) {
                return String.format("chat_%s", chat.get().getId());
            } else {
                Chat newChat = new Chat(uuids.get(0), uuids.get(1));
                chatRepository.save(newChat);
                return String.format("chat_%s", newChat.getId());
            }
        } catch (Exception e) {
            log.error("Error getting chat name: {}", e.getMessage(), e);
            throw new RuntimeException("Error getting chat name.", e);
        }

    }
}
