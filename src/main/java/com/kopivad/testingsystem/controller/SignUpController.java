package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.form.SignUpForm;
import com.kopivad.testingsystem.model.Role;
import com.kopivad.testingsystem.model.User;
import com.kopivad.testingsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@AllArgsConstructor
public class SignUpController {
    private final UserService userService;

    @PostMapping(path = "/signup")
    public String signUp(@Valid SignUpForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("form", form);
            return "signUp";
        }

        userService.saveUser(form);
        return "redirect:/login";
    }

    @GetMapping(path = "/signup")
    public String getSignUpPage(SignUpForm form, Model model) {
        model.addAttribute("form", form);
        return "signUp";
    }
}
