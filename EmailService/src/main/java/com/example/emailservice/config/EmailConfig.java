package com.example.emailservice.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    private final Dotenv dotenv;

    public EmailConfig(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("email-smtp.ap-southeast-1.amazonaws.com");
        mailSender.setPort(587);
        mailSender.setUsername(dotenv.get("AWS-SES-USERNAME"));
        mailSender.setPassword(dotenv.get("AWS-SES-PASSWORD"));
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");

        return mailSender;
    }
}
