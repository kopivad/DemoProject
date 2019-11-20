package com.kopivad.testingsystem.form;

import lombok.Data;

@Data
public class AnswerForm {
    private Long questionId;
    private String rightAnswer;
    private String firstAnswer;
    private String secondAnswer;
    private String thirdAnswer;
}
