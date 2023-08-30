package com.chatus.usermodule.controller;

import com.chatus.usermodule.dto.UserDto;
import com.chatus.usermodule.entity.User;
import com.chatus.usermodule.service.UserService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/v1/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
        try {
            User user = userService.createUser(userDto);
            return ResponseEntity.ok().body(user);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
