package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class UserController {
    @GetMapping(path = "user/{id}")
    public String profile(@PathVariable(name = "id") Long id, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("id", id);
        model.addAttribute("user", user);
        return "profile";
    }
}
