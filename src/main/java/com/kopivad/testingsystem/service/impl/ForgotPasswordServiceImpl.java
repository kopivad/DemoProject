package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.model.User;
import com.kopivad.testingsystem.service.ForgotPasswordService;
import com.kopivad.testingsystem.service.MailService;
import com.kopivad.testingsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    private final UserService userService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void restorePassword(String email) {
        User userByEmail = userService.getUserByEmail(email);
        String newPassword = UUID.randomUUID().toString();
        userByEmail.setPassword(passwordEncoder.encode(newPassword));
        userService.saveUser(userByEmail);
        String text = String.format("Hello, %s. Your password is %s", userByEmail.getNickname(), newPassword);
        mailService.sendMassage(email, "Restore password", text);
    }
}
