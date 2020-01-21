package com.kopivad.testingsystem.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class UserResponseForm {
    private Long sessionId;
    private Long questionId;
    @NotNull(message = "You must choose answer!")
    private Long userAnswerId;
    private Long questionTotalPages;
    private Long questionNumber;
}
