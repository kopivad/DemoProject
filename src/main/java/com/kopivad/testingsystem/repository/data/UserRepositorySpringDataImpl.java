package com.kopivad.testingsystem.repository.data;

import com.kopivad.testingsystem.model.User;
import com.kopivad.testingsystem.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositorySpringDataImpl extends UserRepository, JpaRepository<User, Long> {
    @Override
    User findByEmail(String email);

    @Override
    default User saveUser(User user) {
        return save(user);
    }

    @Override
    User findUserById(Long id);
}
