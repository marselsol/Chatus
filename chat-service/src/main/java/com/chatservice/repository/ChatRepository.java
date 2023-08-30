package com.chatservice.repository;

import com.chatservice.entity.Chat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends CrudRepository<Chat, UUID> {

    Optional<Chat> findChatByUserIdFirstAndUserIdSecond(UUID userIdFirst, UUID userIdSecond);
}
