package com.example.emailservice.controller;

import com.example.emailservice.model.EmailInfo;
import com.example.emailservice.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String sendSimpleEmail(@RequestBody EmailInfo emailInfo) {
        emailService.simpleSendEmail(emailInfo);
        return "Message Queued";
    }
}
