package com.front.service;

import com.front.entity.Message;
import com.front.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class FrontService {

    private List<Message> messages = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private Map<UUID, String> userLoginMap = new HashMap<>();

    public void sendMessage(UUID fromUserId, Message message) {
        message.setId(UUID.randomUUID());
        message.setCreated(new Date());
        message.setFromUserId(fromUserId);
        messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }

    @PostConstruct
    public void createUsers() {
        User user1 = new User(UUID.fromString("47377fcb-3d94-4af2-9707-7593a20f6f7a"), "user1", "user1email", "user1pass", "", LocalDateTime.now(), null);
        User user2 = new User(UUID.fromString("b7810996-b21f-42d2-9077-cae116f7773b"), "user2", "user2email", "user2pass", "", LocalDateTime.now(), null);
        users.add(user1);
        users.add(user2);
        for (User user : users) {
            userLoginMap.put(user.getId(), user.getLogin());
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public Map<UUID, String> getUserLoginMap() {
        return userLoginMap;
    }
}
