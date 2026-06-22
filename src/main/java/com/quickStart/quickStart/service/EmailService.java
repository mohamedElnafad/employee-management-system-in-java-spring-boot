package com.quickStart.quickStart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${MAIL_USERNAME")
    private String fromEmail;

    @Value("${SIGNING_MAIL}")
    private String signingMail;

    public void sendEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(fromEmail);
        message.setSubject("Welcome onboard");
        message.setText("You can create new account form this link, " + signingMail + "?token=" + token);
        mailSender.send(message);
    }
}
