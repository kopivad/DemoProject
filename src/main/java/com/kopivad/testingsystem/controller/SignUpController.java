package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.form.SignUpForm;
import com.kopivad.testingsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class SignUpController {
    private final UserService userService;

    @PostMapping(path = "/signup")
    public String signUp(@Valid SignUpForm form, BindingResult bindingResult, Model model) {
        if (userService.isUserExistByEmail(form.getEmail())) {
            model.addAttribute("emailError", String.format("User with email %s exist!", form.getEmail()));
            model.addAttribute("form" ,form);
            return "signUp";
        }
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(ControllerUtils.getErrors(bindingResult));
            model.addAttribute("form" ,form);
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
