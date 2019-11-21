package com.kopivad.testingsystem.form;

import lombok.Data;

@Data
public class UserQuestionResponseForm {
    private String sessionCode;
    private Long questionId;
    private Long userAnswerId;
    private Long questionTotalPages;
    private Long questionNumber;
}
