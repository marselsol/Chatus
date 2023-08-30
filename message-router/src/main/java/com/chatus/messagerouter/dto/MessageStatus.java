package com.chatus.messagerouter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MessageStatus {
    @JsonProperty
    RECEIVED_BY_SERVER("ПОЛУЧЕНО СЕРВЕРОМ"),

    @JsonProperty
    RECEIVED_BY_USER("ПОЛУЧЕНО ПОЛЬЗОВАТЕЛЕМ"),

    @JsonProperty
    READ("ПРОЧИТАНО");

    private final String messageStatus;

    MessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getStatus() {
        return messageStatus;
    }

}