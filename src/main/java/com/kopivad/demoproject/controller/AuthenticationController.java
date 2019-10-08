package com.kopivad.demoproject.controller;

import com.kopivad.demoproject.model.Role;
import com.kopivad.demoproject.model.User;
import com.kopivad.demoproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class AuthenticationController {
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/signUp")
    public String getSignUpPage() {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String addNewUser(User user, Model model) {
        if (userService.isUserExist(user)) {
            model.addAttribute("message", "User exist");
            return "signUp";
        }
        user.setRoles(Collections.singleton(Role.STUDENT));
        userService.addNewUser(user);
        return "redirect:/login";
    }
}
