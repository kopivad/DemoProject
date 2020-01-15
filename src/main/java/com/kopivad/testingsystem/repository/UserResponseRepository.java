package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.domain.UserResponse;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface UserResponseRepository {
    UserResponse save(UserResponse userAnswer);
    List<UserResponse> findAllByQuestionId(Long id);
    List<UserResponse> findAllByAnswerId(Long id);
    List<UserResponse> findAllByQuizSessionId(Long sessionId);
    boolean isUserResponceExist(Long questionId, Long sessionId);
}
