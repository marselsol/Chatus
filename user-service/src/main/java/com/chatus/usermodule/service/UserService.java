package com.chatus.usermodule.service;

import com.chatus.usermodule.dto.UserDto;
import com.chatus.usermodule.entity.User;
import com.chatus.usermodule.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(UserDto userDto) {
        if (userRepository.findUserByLogin(userDto.getLogin()) != null) {
            throw new EntityExistsException(String.format("Пользователь с логином '%s' уже существует", userDto.getLogin()));
        }
        if (userRepository.findUserByEmail(userDto.getEmail()) != null) {
            throw new EntityExistsException(String.format("Пользователь с почтой '%s' уже существует", userDto.getEmail()));
        }
        if (userDto.getNumberPhone() != null
                && userRepository.findUserByNumberPhone(userDto.getNumberPhone()) != null) {
            throw new EntityExistsException(String.format("Пользователь с номером телефона '%s' уже существует", userDto.getNumberPhone()));
        }
        User user = new User(userDto.getLogin(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getNumberPhone(),
                LocalDateTime.now(),
                LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

    @Transactional
    public String getUserEmailById(String uuid) {
        System.out.println(UUID.fromString(uuid));
        Optional<User> user = userRepository.findUserById(UUID.fromString(uuid));
        if (user.isPresent()) {
            return user.get().getEmail();
        } else {
            throw new NoSuchElementException("User not found with id: " + uuid);
        }
    }
}
