package com.ss.mailsender.services;

import com.ss.mailsender.model.Email;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;
    private static final String SEND_FROM = "johndoe@example.com";

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(List<String> emailData) {
        var mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(SEND_FROM);
        mailMessage.setTo(emailData.get(0));
        mailMessage.setSubject(emailData.get(1));
        mailMessage.setText(emailData.get(2));

        javaMailSender.send(mailMessage);
    }
}
