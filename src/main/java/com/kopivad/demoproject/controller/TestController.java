package com.kopivad.demoproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping("/all/{id}")
    public String getAllTestsByTopicPage(@PathVariable String id) {
        return "testAll";
    }
}
