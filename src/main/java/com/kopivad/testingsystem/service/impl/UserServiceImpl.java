package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.model.User;
import com.kopivad.testingsystem.repository.UserRepository;
import com.kopivad.testingsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
