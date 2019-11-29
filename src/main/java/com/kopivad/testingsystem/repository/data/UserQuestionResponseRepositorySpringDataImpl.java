package com.kopivad.testingsystem.repository.data;

import com.kopivad.testingsystem.model.UserQuestionResponse;
import com.kopivad.testingsystem.repository.UserQuestionResponseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuestionResponseRepositorySpringDataImpl extends UserQuestionResponseRepository, JpaRepository<UserQuestionResponse, String> {
    @Override
    List<UserQuestionResponse> findAllBySessionCode(String secodessionId);

    @Override
    UserQuestionResponse save(UserQuestionResponse userAnswer);
}
