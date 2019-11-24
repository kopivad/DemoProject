package com.kopivad.testingsystem.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserQuestionResponseForm {
    private String sessionCode;
    private Long questionId;
    private Long userAnswerId;
    private Long questionTotalPages;
    private Long questionNumber;
}
