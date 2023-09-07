package com.chatus.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserNotificationPermissions {

    @Id
    private UUID id;
    @Column
    private Boolean agreeEmailNotification;
    @Column
    private Boolean agreePushNotification;
    @Column
    private Boolean agreeSmsNotification;
}
