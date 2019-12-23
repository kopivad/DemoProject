package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.form.UserResponseForm;
import com.kopivad.testingsystem.model.UserResponce;
import com.kopivad.testingsystem.repository.UserResponseRepository;
import com.kopivad.testingsystem.service.AnswerService;
import com.kopivad.testingsystem.service.QuestionService;
import com.kopivad.testingsystem.service.QuizSessionService;
import com.kopivad.testingsystem.service.UserResponseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserResponseServiceImpl implements UserResponseService {
    private final UserResponseRepository userAnswerRepository;
    private final QuizSessionService quizSessionService;
    private final AnswerService answerService;
    private final QuestionService questionService;

    @Override
    public UserResponce saveUserResponse(UserResponce userAnswer) {
        return userAnswerRepository.save(userAnswer);
    }

    @Override
    public List<UserResponce> getAllResponseBySessionId(Long sessionId) {
        return userAnswerRepository.findAllByQuizSessionId(sessionId);
    }

    @Override
    public List<UserResponce> getAllByQuestionId(Long id) {
        return userAnswerRepository.findAllByQuestionId(id);
    }

    @Override
    public UserResponce saveUserResponse(UserResponseForm userResponseForm) {
        return this.saveUserResponse(getUserResponceFromForm(userResponseForm));
    }

    public UserResponce getUserResponceFromForm(UserResponseForm form) {
        return UserResponce
                .builder()
                .quizSession(quizSessionService.getQuizSessionById(form.getSessionId()))
                .answer(answerService.getAnswerById(form.getUserAnswerId()))
                .question(questionService.getQuestionById(form.getQuestionId()))
                .build();
    }
}
