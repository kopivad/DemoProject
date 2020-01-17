package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.form.UserResponseForm;
import com.kopivad.testingsystem.domain.UserResponse;
import com.kopivad.testingsystem.repository.AnswerRepository;
import com.kopivad.testingsystem.repository.QuestionRepository;
import com.kopivad.testingsystem.repository.QuizSessionRepository;
import com.kopivad.testingsystem.repository.UserResponseRepository;
import com.kopivad.testingsystem.repository.jooq.RepositoryUtils;
import com.kopivad.testingsystem.service.UserResponseService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserResponseServiceImpl implements UserResponseService {
    private final UserResponseRepository userResponseRepository;
    private final QuizSessionRepository quizSessionRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final RepositoryUtils repositoryUtils;

    @Override
    public UserResponse saveUserResponse(UserResponse userResponse) {
        if (userResponseRepository.isUserResponceExist(userResponse.getQuestion().getId(), userResponse.getQuizSession().getId()))
            return userResponse;
        UserResponse responseFromDB = userResponseRepository.save(userResponse);
        return repositoryUtils.getFullUserResponse(responseFromDB);
    }

    @Override
    public List<UserResponse> getAllResponseBySessionId(Long sessionId) {
        List<UserResponse> responsesFromDB = userResponseRepository.findAllByQuizSessionId(sessionId);
        return responsesFromDB
                .stream()
                .map(repositoryUtils::getFullUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse saveUserResponse(UserResponseForm userResponseForm) {
        return this.saveUserResponse(getUserResponceFromForm(userResponseForm));
    }

    @Override
    public List<UserResponse> getAllUserResponseBySessionId(Long sessionId) {
        List<UserResponse> responseFromDB = userResponseRepository.findAllByQuizSessionId(sessionId);
        return responseFromDB
                .stream()
                .map(repositoryUtils::getFullUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getAllResponseByAnswerId(Long id) {
        List<UserResponse> questionFromDB = userResponseRepository.findAllByAnswerId(id);
        return questionFromDB
                .stream()
                .map(repositoryUtils::getFullUserResponse)
                .collect(Collectors.toList());
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
