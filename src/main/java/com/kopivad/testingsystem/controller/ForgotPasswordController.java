package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.form.ForgetPasswordForm;
import com.kopivad.testingsystem.service.ForgotPasswordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class ForgotPasswordController {
    private final ForgotPasswordService forgotPasswordService;

    @GetMapping(path = "forgot")
    public String forgotPasswordPage() {
        return "forgot";
    }

    @PostMapping(path = "forgot")
    public String forgotPassword(ForgetPasswordForm forgetPasswordForm) {
        forgotPasswordService.restorePassword(forgetPasswordForm.getEmail());
        return "/login";
    }
}
