package com.kopivad.testingsystem.repository.data;

import com.kopivad.testingsystem.model.UserQuestionResponse;
import com.kopivad.testingsystem.repository.UserQuestionResponseRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface UserQuestionResponseRepositorySpringDataImpl extends UserQuestionResponseRepository, JpaRepository<UserQuestionResponse, String> {
    @Override
    List<UserQuestionResponse> findAllBySessionCode(String sessionCode);

    @Override
    UserQuestionResponse save(UserQuestionResponse userAnswer);

    @Override
    List<UserQuestionResponse> findAllByQuestionId(Long id);

    @Override
    List<UserQuestionResponse> findAllByAnswerId(Long id);

}
