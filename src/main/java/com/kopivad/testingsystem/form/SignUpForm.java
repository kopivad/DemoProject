package com.kopivad.testingsystem.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@AllArgsConstructor
@Getter
@Setter
public class SignUpForm {

    @Email(message = "Invalid email")
    private String email;

    @Min(value = 3, message = "Nickname must be more than 3 letters")
    @Max(value = 25, message = "Nickname must be less than 25 letters")
    private String nickname;

    @Min(value = 5, message = "Password must be more than 3 letters")
    private String password;
}
