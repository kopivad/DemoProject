package com.kopivad.demoproject.service;

import com.kopivad.demoproject.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    boolean isUserExist(User user);
    void addNewUser(User user);

    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
