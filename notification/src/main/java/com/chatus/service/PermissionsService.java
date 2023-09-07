package com.chatus.service;

import com.chatus.entity.UserNotificationPermissions;
import com.chatus.repository.NotificationPermissionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PermissionsService {

    private Map<UUID, UserNotificationPermissions> permissoinsMap = new HashMap<>();

    private final NotificationPermissionsRepository notificationPermissionsRepository;

    public PermissionsService(NotificationPermissionsRepository notificationPermissionsRepository) {
        this.notificationPermissionsRepository = notificationPermissionsRepository;
    }

    public void addPermissionToMap(UUID userNotificationPermissionsUUID) {
        System.out.println();
        Optional<UserNotificationPermissions> permission = notificationPermissionsRepository.findById(userNotificationPermissionsUUID);
        if (permission.isPresent()) {
            UserNotificationPermissions userNotificationPermissions = permission.get();
            permissoinsMap.put(userNotificationPermissions.getId(), userNotificationPermissions);
        } else {
            throw new NoSuchElementException("Permission not found for UUID: " + userNotificationPermissionsUUID);
        }
    }

    public UserNotificationPermissions getUserNotificationPermissions(UUID UserNotificationPermissionsUUID) {
        if (!permissoinsMap.containsKey(UserNotificationPermissionsUUID)) {
            addPermissionToMap(UserNotificationPermissionsUUID);
        }
        return permissoinsMap.get(UserNotificationPermissionsUUID);
    }


}
