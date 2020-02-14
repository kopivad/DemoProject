package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.Mail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

class MailServiceImplTest {
    @InjectMocks
    private MailServiceImpl mailService;
    @Mock
    private JavaMailSender emailSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void sendMassage() {
        mailService.sendMessage(Mail.builder().receiver("email@test.com").text("Some text").subject("Some subject").build());
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}