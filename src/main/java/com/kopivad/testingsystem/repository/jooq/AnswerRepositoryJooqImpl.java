package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.model.Answer;
import com.kopivad.testingsystem.repository.AnswerRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public class AnswerRepositoryJooqImpl implements AnswerRepository {

    @Override
    public List<Answer> findAllByQuestionId(Long id) {
        return null;
    }

    @Override
    public List<Answer> findAll() {
        return null;
    }

    @Override
    public Answer save(Answer answer) {
        return null;
    }

    @Override
    public Answer findAnswerById(Long id) {
        return null;
    }
}
