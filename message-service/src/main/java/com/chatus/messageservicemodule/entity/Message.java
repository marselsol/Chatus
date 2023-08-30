package com.chatus.messageservicemodule.entity;

import com.chatus.messageservicemodule.dto.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Message implements Serializable {

    private UUID id;
    private Date created;
    private Date updated;
    private String text;
    private UUID fromUserId;
    private UUID messageToId;
    private List<UUID> attach;
    private MessageStatus messageStatus;
}