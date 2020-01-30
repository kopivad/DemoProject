package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.UserResponse;
import com.kopivad.testingsystem.form.UserResponseForm;
import com.kopivad.testingsystem.repository.AnswerRepository;
import com.kopivad.testingsystem.repository.QuestionRepository;
import com.kopivad.testingsystem.repository.QuizSessionRepository;
import com.kopivad.testingsystem.repository.UserResponseRepository;
import com.kopivad.testingsystem.service.UserResponseService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserResponseServiceImpl implements UserResponseService {
    private final UserResponseRepository userResponseRepository;
    private final QuizSessionRepository quizSessionRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Override
    public UserResponse saveUserResponse(UserResponse userResponse) {
        if (userResponseRepository.isUserResponceExist(userResponse.getQuestion().getId(), userResponse.getQuizSession().getId()))
            return userResponse;
        return userResponseRepository.save(userResponse);
    }

    @Override
    public List<UserResponse> getAllResponseBySessionId(Long sessionId) {
        return userResponseRepository.findAllByQuizSessionId(sessionId);
    }

    @Override
    public UserResponse saveUserResponse(UserResponseForm userResponseForm) {
        return this.saveUserResponse(getUserResponceFromForm(userResponseForm));
    }

    @Override
    public List<UserResponse> getAllUserResponseBySessionId(Long sessionId) {
        return userResponseRepository.findAllByQuizSessionId(sessionId);
    }

    @Override
    public List<UserResponse> getAllResponseByAnswerId(Long id) {
        return userResponseRepository.findAllByAnswerId(id);
    }

    @SneakyThrows
    public UserResponse getUserResponceFromForm(UserResponseForm form) {
        return UserResponse
                .builder()
                .quizSession(quizSessionRepository.findQuizSessionById(form.getSessionId()))
                .answer(answerRepository.findAnswerById(form.getUserAnswerId()))
                .question(questionRepository.findQuestionById(form.getQuestionId()))
                .build();
    }
}
