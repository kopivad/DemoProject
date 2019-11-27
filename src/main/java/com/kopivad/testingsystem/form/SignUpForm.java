package com.kopivad.testingsystem.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SignUpForm {
    private String email;
    private String nickname;
    private String password;
}
