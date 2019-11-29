package com.kopivad.testingsystem.repository.data;

import com.kopivad.testingsystem.model.Answer;
import com.kopivad.testingsystem.repository.AnswerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepositorySpringDataImpl extends AnswerRepository, JpaRepository<Answer, Long> {
    @Override
    List<Answer> findAllByQuestionId(Long id);

    @Override
    List<Answer> findAll();

    @Override
    Answer save(Answer answer);

    @Override
    Answer findAnswerById(Long id);
}
