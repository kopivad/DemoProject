package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.form.ForgetPasswordForm;
import com.kopivad.testingsystem.service.ForgotPasswordService;
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
public class ForgotPasswordController {
    private final ForgotPasswordService forgotPasswordService;
    private final UserService userService;

    @GetMapping(path = "forgot")
    public String forgotPasswordPage() {
        return "forgot";
    }

    @PostMapping(path = "forgot")
    public String forgotPassword(@Valid ForgetPasswordForm forgetPasswordForm, BindingResult bindingResult, Model model) {
        if (!userService.isUserExistByEmail(forgetPasswordForm.getEmail())) {
            model.addAttribute("emailError", "User not found!");
            return "forgot";
        }
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(ControllerUtils.getErrors(bindingResult));
            return "forgot";
        }
        forgotPasswordService.restorePassword(forgetPasswordForm.getEmail());
        return "/login";
    }
}
