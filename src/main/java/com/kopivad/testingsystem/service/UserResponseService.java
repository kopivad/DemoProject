package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.form.UserResponseForm;
import com.kopivad.testingsystem.model.UserResponce;

import java.util.List;

public interface UserResponseService {
    UserResponce saveUserResponse(UserResponce userAnswer);

    List<UserResponce> getAllResponseBySessionId(Long sessionId);

    List<UserResponce> getAllByQuestionId(Long id);

    UserResponce saveUserResponse(UserResponseForm userResponseForm);
}
