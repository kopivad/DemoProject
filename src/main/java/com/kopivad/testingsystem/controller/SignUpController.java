package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.form.SignUpForm;
import com.kopivad.testingsystem.model.Role;
import com.kopivad.testingsystem.model.User;
import com.kopivad.testingsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
@AllArgsConstructor
public class SignUpController {
    private final UserService userService;

    @PostMapping(path = "/signup")
    public String signUp(SignUpForm signUpForm) {
        User newUser = new User();
        newUser.setEmail(signUpForm.getEmail());
        newUser.setNickname(signUpForm.getNickname());
        newUser.setPassword(signUpForm.getPassword());
        newUser.setRoles(Collections.singleton(Role.USER));
        userService.saveUser(newUser);
        return "redirect:/login";
    }

    @GetMapping(path = "/signup")
    public String getSignUpPage() {
       return "signUp";
    }
}
