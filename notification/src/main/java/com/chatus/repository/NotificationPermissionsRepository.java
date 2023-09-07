package com.chatus.repository;

import com.chatus.entity.UserNotificationPermissions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationPermissionsRepository extends CrudRepository<UserNotificationPermissions, UUID> {
}