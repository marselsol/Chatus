package com.chatus.messageservicemodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MessageServiceModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageServiceModuleApplication.class, args);
    }

}
