package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.model.UserResponce;

import java.util.List;

public interface UserResponseService {
    void saveUserResponse(UserResponce userAnswer);

    List<UserResponce> getAllResponseBySessionId(Long sessionId);

    List<UserResponce> getAllByQuestionId(Long id);
}
