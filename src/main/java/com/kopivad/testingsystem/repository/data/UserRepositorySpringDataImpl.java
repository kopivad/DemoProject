package com.kopivad.testingsystem.repository.data;

import com.kopivad.testingsystem.model.User;
import com.kopivad.testingsystem.repository.UserRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositorySpringDataImpl extends UserRepository, CrudRepository<User, Long> {
    @Override
    UserDetails findByEmail(String email);

    @Override
    User save(User user);
}
