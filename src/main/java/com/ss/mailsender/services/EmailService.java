package com.ss.mailsender.services;

import com.ss.mailsender.model.Email;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;
    private static final String SEND_FROM = "johndoe@example.com";

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(Email email) {
        var mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(SEND_FROM);
        mailMessage.setTo(email.getTo());
        mailMessage.setSubject(email.getSubject());
        mailMessage.setText(email.getBody());

        javaMailSender.send(mailMessage);
    }
}
