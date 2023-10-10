package com.chatus.usermodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class UserModuleApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserModuleApplication.class, args);
    }

}
