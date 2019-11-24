package com.kopivad.testingsystem.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class QuizForm {
    private String title;
    private String description;
    private Long quizId;
}
