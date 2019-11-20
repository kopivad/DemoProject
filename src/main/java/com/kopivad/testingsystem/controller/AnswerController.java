package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.form.AnswerForm;
import com.kopivad.testingsystem.model.Answer;
import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.service.AnswerService;
import com.kopivad.testingsystem.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    public AnswerController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @PostMapping(path = "/answer/add")
    public String addAnswer(AnswerForm answerForm) {
        Question currentQuestion = questionService.getQuestionById(answerForm.getQuestionId()).get();
        answerService.saveAnswer(new Answer(answerForm.getFirstAnswer(), false, currentQuestion));
        answerService.saveAnswer(new Answer(answerForm.getSecondAnswer(), false, currentQuestion));
        answerService.saveAnswer(new Answer(answerForm.getThirdAnswer(), false, currentQuestion));
        answerService.saveAnswer( new Answer(answerForm.getRightAnswer(), true, currentQuestion));

        return String.format("redirect:/quiz/%d/question/all", currentQuestion.getQuiz().getId());
    }


    @GetMapping(path = "/answer/add/{questionId}")
    public String getAddAnswerPage(@PathVariable(name = "questionId") Long id, Model model) {
        model.addAttribute("questionId", id);
        return "answerAdd";
    }
}
