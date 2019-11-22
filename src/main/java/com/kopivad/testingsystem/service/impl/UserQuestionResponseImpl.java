package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.model.UserQuestionResponse;
import com.kopivad.testingsystem.repository.UserQuestionResponseRepository;
import com.kopivad.testingsystem.service.UserQuestionResponseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserQuestionResponseImpl implements UserQuestionResponseService {
    private final UserQuestionResponseRepository userAnswerRepository;

    public UserQuestionResponseImpl(UserQuestionResponseRepository userAnswerRepository) {
        this.userAnswerRepository = userAnswerRepository;
    }

    @Override
    public void saveUserResponse(UserQuestionResponse userAnswer) {
        userAnswerRepository.save(userAnswer);
    }

    @Override
    public List<UserQuestionResponse> getAllResponseByCode(String code) {
        return userAnswerRepository.findAllBySessionCode(code);
    }
}
