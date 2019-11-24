package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.model.UserQuestionResponse;
import com.kopivad.testingsystem.repository.UserQuestionResponseRepository;
import com.kopivad.testingsystem.service.UserQuestionResponseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserQuestionResponseImpl implements UserQuestionResponseService {
    private final UserQuestionResponseRepository userAnswerRepository;

    @Override
    public void saveUserResponse(UserQuestionResponse userAnswer) {
        userAnswerRepository.save(userAnswer);
    }

    @Override
    public List<UserQuestionResponse> getAllResponseByCode(String code) {
        return userAnswerRepository.findAllBySessionCode(code);
    }
}
