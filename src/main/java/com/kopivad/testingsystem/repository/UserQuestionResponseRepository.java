package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.model.UserQuestionResponse;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface UserQuestionResponseRepository {
    List<UserQuestionResponse> findAllBySessionCode(String secodessionId);

    UserQuestionResponse save(UserQuestionResponse userAnswer);
}
