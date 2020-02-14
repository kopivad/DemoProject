package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.Mail;
import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.service.MailService;
import com.kopivad.testingsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.mockito.Mockito.*;

class ForgotPasswordServiceImplTest {
    @InjectMocks
    private ForgotPasswordServiceImpl forgotPasswordService;
    @Mock
    private UserService userService;
    @Mock
    private MailService mailService;
    @Mock
    private PasswordEncoder passwordEncoder;

    private final String EMAIL = "email@gmail.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void restorePassword() {
        when(userService.getUserByEmail(EMAIL)).thenReturn(new User());
        when(userService.updateUser(any(User.class))).thenReturn(new User());
        when(passwordEncoder.encode(anyString())).thenReturn(UUID.randomUUID().toString());
        forgotPasswordService.restorePassword(EMAIL);
        verify(userService, times(1)).getUserByEmail(EMAIL);
        verify(userService, times(1)).updateUser(any(User.class));
        verify(passwordEncoder, times(1)).encode(any(String.class));
        verify(mailService, times(1)).sendMessage(any(Mail.class));

    }

}