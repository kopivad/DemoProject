package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void saveUser(User user);

    User getUserByEmail(String email);
}
