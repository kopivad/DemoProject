package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.model.UserResponce;
import com.kopivad.testingsystem.repository.UserResponseRepository;
import com.kopivad.testingsystem.service.UserResponseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserResponseServiceImpl implements UserResponseService {
    private final UserResponseRepository userAnswerRepository;

    @Override
    public void saveUserResponse(UserResponce userAnswer) {
        userAnswerRepository.save(userAnswer);
    }

    @Override
    public List<UserResponce> getAllResponseBySessionId(Long sessionId) {
        return userAnswerRepository.findAllByQuizSessionId(sessionId);
    }

    @Override
    public List<UserResponce> getAllByQuestionId(Long id) {
        return userAnswerRepository.findAllByQuestionId(id);
    }
}
