package com.example.emailservice.service;

import com.example.emailservice.model.EmailInfo;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {


    private final JavaMailSender emailSender;

    @Value("${fromEmail}")
    private String fromEmail;

    @Value("${fromName}")
    private String fromName;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    @Async
    public void simpleSendEmail(EmailInfo emailInfo) {
        log.info("simpleSend toEmail: {} subJect: {} emailMessage: {}", emailInfo.getToEmail(), emailInfo.getSubject(), emailInfo.getMessage());
        try {
            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(fromEmail, fromName);
            helper.setTo(emailInfo.getToEmail());
            helper.setSubject(emailInfo.getSubject());
            helper.setText(emailInfo.getMessage(), true);

            emailSender.send(message);
            log.info("simpleSend: Email Queued");

        }
        catch (Exception e) {
            log.error("Exception: {}", e.getMessage());
        }


    }
}
