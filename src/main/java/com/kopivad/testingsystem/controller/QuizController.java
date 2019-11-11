package com.kopivad.testingsystem.controller;


import com.kopivad.testingsystem.form.QuizForm;
import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.service.QuestionService;
import com.kopivad.testingsystem.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static java.lang.String.format;

@Controller
public class QuizController {
    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
    }

    @GetMapping(path = "quiz")
    public String getQuizPage(Model model, QuizForm quizForm) {
        model.addAttribute("quizForm", quizForm);
        model.addAttribute("quizzes", quizService.getAllQuizzes());
        return "quiz";
    }

    @PostMapping(path = "quiz")
    public String saveQuiz(@ModelAttribute QuizForm quizForm) {
        Quiz newQuiz = new Quiz();
        newQuiz.setTitle(quizForm.getTitle());

        quizService.saveQuiz(newQuiz);
        return "redirect:/quiz";
    }

    @GetMapping(path = "quiz/{id}")
    public String startQuiz(@PathVariable(name = "id") Long quizId) {
        return format("redirect:/quiz/%d/question/1", quizId);
    }
}
