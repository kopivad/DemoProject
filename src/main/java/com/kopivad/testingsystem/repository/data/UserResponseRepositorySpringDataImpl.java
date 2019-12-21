package com.kopivad.testingsystem.repository.data;

import com.kopivad.testingsystem.model.UserResponce;
import com.kopivad.testingsystem.repository.UserResponseRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserResponseRepositorySpringDataImpl extends UserResponseRepository, JpaRepository<UserResponce, String> {
    @Override
    UserResponce save(UserResponce userAnswer);

    @Override
    List<UserResponce> findAllByQuestionId(Long id);

    @Override
    List<UserResponce> findAllByAnswerId(Long id);

    @Override
    List<UserResponce> findAllByQuizSessionId(Long sessionId);

}
