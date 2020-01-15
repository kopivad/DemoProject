package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.form.UserResponseForm;
import com.kopivad.testingsystem.domain.UserResponse;

import java.util.List;

public interface UserResponseService {
    UserResponse saveUserResponse(UserResponse userAnswer);
    List<UserResponse> getAllResponseBySessionId(Long sessionId);
    UserResponse saveUserResponse(UserResponseForm userResponseForm);
    List<UserResponse> getAllUserResponseBySessionId(Long sessionId);
    List<UserResponse> getAllResponseByAnswerId(Long id);
}
