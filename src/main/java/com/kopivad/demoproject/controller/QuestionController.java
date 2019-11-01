package com.kopivad.demoproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "questions")
public class QuestionController {
    @GetMapping("/all")
    public String getAllQuestionPage() {
        return "questionAll";
    }
}

