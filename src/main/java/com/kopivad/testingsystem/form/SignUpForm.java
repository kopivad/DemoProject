package com.kopivad.testingsystem.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@Getter
@Setter
public class SignUpForm {

    @Email(message = "Invalid email")
    @NotBlank(message = "Email can not be empty")
    private String email;

    @NotBlank(message = "Nickname can not be empty")
    private String nickname;

    @NotBlank(message = "Password can not be empty")
    private String password;
}
