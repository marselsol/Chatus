package com.front.utils;

import com.front.entity.Message;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class GenerateMessage {
    int count = 0;

    public Message generateMessage(String text) {
        Message message = new Message();
        message.setId(UUID.randomUUID());
        message.setCreated(new Date());
        message.setText(text);
        return message;
    }

    public Message generateMessageRandom() {
        Message message = new Message();
        message.setId(UUID.randomUUID());
        message.setCreated(new Date());
        message.setText("message_" + count);
        count++;
        message.setFromUserId(UUID.fromString("47377fcb-3d94-4af2-9707-7593a20f6f7a"));
        message.setMessageToId(UUID.fromString("b7810996-b21f-42d2-9077-cae116f7773b"));
        return message;
    }
}
