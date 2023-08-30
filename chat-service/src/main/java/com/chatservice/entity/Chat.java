package com.chatservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID userIdFirst;

    @Column(nullable = false)
    private UUID userIdSecond;

    @Builder
    public Chat(UUID userIdFirst, UUID userIdSecond) {
        this.userIdFirst = userIdFirst;
        this.userIdSecond = userIdSecond;
    }
}
