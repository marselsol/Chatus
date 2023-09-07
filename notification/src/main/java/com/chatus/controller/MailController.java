package com.chatus.controller;

import com.chatus.utils.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    private final EmailService emailService;

    public MailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String sendEmail() {
        emailService.sendEmail("marsspam@mail.ru", "Test", "Test message");
        return "Sent";
    }
}
