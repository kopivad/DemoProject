package com.kopivad.demoproject.controller;

import com.kopivad.demoproject.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String homePage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("currentUser", user);
        return "home";
    }
}
