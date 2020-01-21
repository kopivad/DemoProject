package com.kopivad.testingsystem.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
public class QuizForm {
    @NotBlank(message = "Title can`t be empty")
    private String title;
    @NotBlank(message = "Title can`t be empty")
    private String description;
    private Long quizId;
    private Long authorId;
}
