package com.kopivad.testingsystem.form;

import lombok.Data;

@Data
public class AnswerForm {
    private Long questionId;
    private Long answerId;
    private String isRight;
    private String text;
}
