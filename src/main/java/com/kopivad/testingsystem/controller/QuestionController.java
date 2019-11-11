package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping(path = "quiz/{quizId}/question/{questionId}")
    public String getQuestionByIdAndQuizId(@PathVariable(name = "quizId") Long quizId,
                                           @PathVariable(name = "questionId") Integer questionId,
                                           Model model
    ) {
        Pageable pageable = PageRequest.of(questionId ,1);
        Page<Question> questions = questionService.getQuestionsByQuizId(quizId, pageable);
        model.addAttribute("questions", questions);
        return "question";
    }

    @GetMapping(path = "/question/new/{countOfAnswers}")
    public String addQuestionPage(@PathVariable(name = "countOfAnswers") Integer count, Model model) {
        model.addAttribute("countOfAnswers", count);
        return "questionAdd";
    }

    @PostMapping(path = "/question")
    public String saveQuestion(@RequestParam String title) {

        return "redirect:/question";
    }
}
