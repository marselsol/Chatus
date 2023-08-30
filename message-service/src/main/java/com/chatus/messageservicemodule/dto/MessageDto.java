package com.chatus.messageservicemodule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class MessageDto {

    @NotNull(message = "field cannot be null")
    private Date created;
    private String text;
    @NotNull
    private UUID fromUserId;
    @NotNull
    private UUID messageToId;
    private List<UUID> attach;
}
