package com.kopivad.testingsystem.repository;


import com.kopivad.testingsystem.exception.UserNotFoundException;
import com.kopivad.testingsystem.domain.User;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@NoRepositoryBean
public interface UserRepository {
    User findByEmail(String email) throws UsernameNotFoundException, UserNotFoundException;
    User saveUser(User user);
    User findUserById(Long userId);
}
