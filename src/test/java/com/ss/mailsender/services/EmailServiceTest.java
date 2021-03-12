package com.ss.mailsender.services;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Disabled
    @Test
    void sendMail() {
        emailService.sendMail(List.of("v@k.r", "some subject", "Some Body"));
    }
}
