package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.model.QuizSession;
import com.kopivad.testingsystem.model.UserQuestionResponse;
import com.kopivad.testingsystem.service.QuestionService;
import com.kopivad.testingsystem.service.QuizService;
import com.kopivad.testingsystem.service.QuizSessionService;
import com.kopivad.testingsystem.service.UserQuestionResponseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class QuizResultController {
    private final QuizService quizService;
    private final UserQuestionResponseService userQuestionResponseService;
    private final QuestionService questionService;
    private final QuizSessionService quizSessionService;
    
    @GetMapping(path = "quiz/result/")
    public String resultPage(@RequestParam(name = "code") String code, Model model) {
        long countOfCorrect = quizService.getCountOfCorrectAnswersBySessionCode(code);
        long totalAnswers = quizService.getTotalOfAnswersBySessionCode(code);
        float percentageOfCorrectAnswers = quizService.getPercentageOfCorrectAnswers(countOfCorrect, totalAnswers);
        model.addAttribute("correctAmount", countOfCorrect);
        model.addAttribute("code", code);
        model.addAttribute("totalAmount", totalAnswers);
        model.addAttribute("percentageAmount", percentageOfCorrectAnswers);
        return "quizResult";
    }

    @GetMapping(path = "/quiz/check/{code}")
    public String checkResultPage(@PathVariable(name = "code") String code, Model model) {
        QuizSession quizSession = quizSessionService.getSessionByCode(code);
        long countOfCorrect = quizService.getCountOfCorrectAnswersBySessionCode(code);
        long totalAnswers = quizService.getTotalOfAnswersBySessionCode(code);
        List<UserQuestionResponse> allResponseByCode = userQuestionResponseService.getAllResponseByCode(code);
        float percentageOfCorrectAnswers = quizService.getPercentageOfCorrectAnswers(countOfCorrect, totalAnswers);
        model.addAttribute("quizTitle", quizSession.getQuiz().getTitle());
        model.addAttribute("correctAmount", countOfCorrect);
        model.addAttribute("totalAmount", totalAnswers);
        model.addAttribute("allResponseByCode", allResponseByCode);
        model.addAttribute("percentageAmount", percentageOfCorrectAnswers);
        model.addAttribute("questions", quizSession.getQuiz().getQuestions());
        return "quizCheck";
    }
}
