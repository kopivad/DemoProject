package com.kopivad.demoproject.controller;

import com.kopivad.demoproject.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String homePage(Model model, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        System.out.println(currentUser.getName());
        model.addAttribute("currentUser", currentUser);
        return "home";
    }
}
