package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.model.UserQuestionResponse;

import java.util.List;

public interface UserQuestionResponseService {
    void saveUserResponse(UserQuestionResponse userAnswer);

    List<UserQuestionResponse> getAllResponseByCode(String code);
}
