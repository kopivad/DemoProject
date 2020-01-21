package com.kopivad.testingsystem.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class QuestionForm {
    @NotBlank(message = "Can`t be empty")
    private String title;
    @NotNull(message = "Can`t be empty")
    private Long quizId;
    private Long questionId;
}
