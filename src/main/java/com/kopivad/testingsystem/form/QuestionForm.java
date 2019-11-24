package com.kopivad.testingsystem.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class QuestionForm {
    private String title;
    private Long quizId;
    private Long questionId;
}
