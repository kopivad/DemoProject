package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.form.QuizForm;
import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class IndexController {
    private final QuizService quizService;

    @GetMapping(path = "/")
    public String getIndexPage() {
        return "redirect:/index";
    }

    @GetMapping(path = "index")
    public String getQuizPage(Model model, QuizForm quizForm, @AuthenticationPrincipal User user) {
        model.addAttribute("quizForm", quizForm);
        List<Quiz> allQuizzes = quizService.getAllQuizzes();
        model.addAttribute("quizzes", allQuizzes);
        model.addAttribute("user", user);
        return "index";
    }
}
