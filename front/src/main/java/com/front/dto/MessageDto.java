package com.front.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class MessageDto {

    private Date created;
    private String text;
    private UUID fromUserId;
    private UUID messageToId;
    private List<UUID> attach;
}
