package com.kopivad.testingsystem.repository;


import com.kopivad.testingsystem.model.User;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.core.userdetails.UserDetails;

@NoRepositoryBean
public interface UserRepository {
    UserDetails findByEmail(String email);

    User save(User user);
}
