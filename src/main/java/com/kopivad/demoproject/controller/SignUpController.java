package com.kopivad.demoproject.controller;

import com.kopivad.demoproject.form.SignUpForm;
import com.kopivad.demoproject.model.Role;
import com.kopivad.demoproject.model.User;
import com.kopivad.demoproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class SignUpController {
    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signUp")
    public String showSignUp(SignUpForm signUpForm, Model model) {
        model.addAttribute("signUpForm", signUpForm);
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid SignUpForm signUpForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "signUp";
        }

        if (userService.isUserExistByEmail(signUpForm.getEmail())) {
            model.addAttribute("message", "User exist");
            return "signUp";
        }

        User newUser = new User();
        newUser.setName(signUpForm.getName());
        newUser.setSurname(signUpForm.getSurname());
        newUser.setEmail(signUpForm.getEmail());
        newUser.setPassword(signUpForm.getPassword());
        newUser.setRoles(Collections.singleton(Role.STUDENT));

        userService.saveNewUser(newUser);
        return "redirect:/login";
    }
}
