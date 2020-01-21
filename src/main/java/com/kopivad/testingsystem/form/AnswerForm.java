package com.kopivad.testingsystem.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class AnswerForm {
    @NotNull(message = "Question can`t be empty")
    private Long questionId;
    @NotNull(message = "Answer can`t be empty")
    private Long answerId;
    private String isRight;
    @NotBlank(message = "Text can`t be empty")
    private String text;
}
