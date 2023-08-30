package com.front.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private UUID id;
    private String login;
    private String email;
    private String password;
    private String numberPhone;
    private LocalDateTime created;
    private LocalDateTime updated;
}



