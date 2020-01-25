package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.form.SignUpForm;
import com.kopivad.testingsystem.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User saveUser(User user);
    User getUserByEmail(String email);
    User getUserById(Long userId);
    User saveUser(SignUpForm signUpForm);
    boolean isUserExistByEmail(String email);
    User updateUser(User user);
}
