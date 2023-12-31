package com.chatus.usermodule.controller;

import com.chatus.usermodule.dto.UserDto;
import com.chatus.usermodule.entity.User;
import com.chatus.usermodule.service.UserService;
import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/v1/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
        try {
            User user = userService.createUser(userDto);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/v1/get-user-email/{uuid}")
    public ResponseEntity<?> getUserEmailById(@PathVariable String uuid) {
        try {
            return ResponseEntity.ok().body(userService.getUserEmailById(uuid));
        } catch (Exception e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
