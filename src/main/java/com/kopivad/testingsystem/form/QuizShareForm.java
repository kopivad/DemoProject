package com.kopivad.testingsystem.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@AllArgsConstructor
@Getter
@Setter
public class QuizShareForm {
    @Email(message = "Wrong email")
    private String email;
    private Long quizId;
}
