package com.chatus.usermodule.repository;

import com.chatus.usermodule.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    User findUserByLogin(String login);
    User findUserByEmail(String email);
    User findUserByNumberPhone(String numberPhone);
    Optional<User> findUserById(UUID userId);
}
