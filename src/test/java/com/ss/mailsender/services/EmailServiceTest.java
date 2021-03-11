package com.ss.mailsender.services;

import com.ss.mailsender.model.Email;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceTest {

    private static final String SEND_TO = "";

    @Autowired
    private EmailService emailService;

    @Test
    void sendMail() {
        Email email = new Email(SEND_TO, "some subject", "Some Body");
        emailService.sendMail(email);
    }
}
