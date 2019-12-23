package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.form.SignUpForm;
import com.kopivad.testingsystem.model.Role;
import com.kopivad.testingsystem.model.User;
import com.kopivad.testingsystem.repository.UserRepository;
import com.kopivad.testingsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.saveUser(user);

    }

    @Override
    public User getUserByEmail(String email) {
        return (User) userRepository.findByEmail(email);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findUserById(userId);
    }

    @Override
    public User saveUser(SignUpForm signUpForm) {
        return this.saveUser(getUserFromForm(signUpForm));
    }

    public User getUserFromForm(SignUpForm form) {
        return User
                .builder()
                .email(form.getEmail())
                .nickname(form.getNickname())
                .roles(Collections.singleton(Role.USER))
                .password(passwordEncoder.encode(form.getPassword()))
                .build();
    }
}
