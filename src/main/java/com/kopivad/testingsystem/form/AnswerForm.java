package com.kopivad.testingsystem.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AnswerForm {
    private Long questionId;
    private Long answerId;
    private String isRight;
    private String text;
}
