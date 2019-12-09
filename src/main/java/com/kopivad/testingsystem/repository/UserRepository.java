package com.kopivad.testingsystem.repository;


import com.kopivad.testingsystem.model.User;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository {
    User findByEmail(String email);

    User saveUser(User user);

    User findUserById(Long userId);
}
