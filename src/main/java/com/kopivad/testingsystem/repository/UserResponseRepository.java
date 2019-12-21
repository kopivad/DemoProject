package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.model.UserResponce;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface UserResponseRepository {

    UserResponce save(UserResponce userAnswer);

    List<UserResponce> findAllByQuestionId(Long id);

    List<UserResponce> findAllByAnswerId(Long id);

    List<UserResponce> findAllByQuizSessionId(Long sessionId);
}
