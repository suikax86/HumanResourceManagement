package com.example.emailservice.service;

import com.example.emailservice.model.EmailInfo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EmailListener {

    private final EmailService emailService;

    public EmailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "emailQueue1")
    public void handleEmailSent(EmailInfo emailInfo) {
        emailService.simpleSendEmail(emailInfo);
    }
}
