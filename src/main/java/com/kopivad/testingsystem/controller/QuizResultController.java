package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.model.*;
import com.kopivad.testingsystem.service.QuestionService;
import com.kopivad.testingsystem.service.QuizService;
import com.kopivad.testingsystem.service.QuizSessionService;
import com.kopivad.testingsystem.service.UserResponseService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final UserResponseService userQuestionResponseService;
    private final QuizSessionService quizSessionService;
    private final QuestionService questionService;
    
    @GetMapping(path = "quiz/result/")
    public String resultPage(@RequestParam(name = "session") Long sessionId, Model model, @AuthenticationPrincipal User user) {
        long countOfCorrect = quizService.getCountOfCorrectAnswersBySessionId(sessionId);
        long totalAnswers = quizService.getTotalOfAnswersBySessionId(sessionId);
        float percentageOfCorrectAnswers = quizService.getPercentageOfCorrectAnswers(countOfCorrect, totalAnswers);
        model.addAttribute("correctAmount", countOfCorrect);
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("totalAmount", totalAnswers);
        model.addAttribute("percentageAmount", percentageOfCorrectAnswers);
        model.addAttribute("user", user);
        return "quizResult";
    }

    @GetMapping(path = "/quiz/check/{session}")
    public String checkResultPage(@PathVariable(name = "session") Long sessionId, Model model, @AuthenticationPrincipal User user) {
        QuizSession quizSession = quizSessionService.getQuizSessionById(sessionId);
        Quiz quiz = quizService.getQuizById(quizSession.getQuiz().getId());
        List<Question> questions = questionService.getQuestionByQuizId(quiz.getId());
        long countOfCorrect = quizService.getCountOfCorrectAnswersBySessionId(sessionId);
        long totalAnswers = quizService.getTotalOfAnswersBySessionId(sessionId);
        List<UserResponce> allResponseSessionId = userQuestionResponseService.getAllResponseBySessionId(sessionId);
        float percentageOfCorrectAnswers = quizService.getPercentageOfCorrectAnswers(countOfCorrect, totalAnswers);

        model.addAttribute("quizTitle", quiz.getTitle());
        model.addAttribute("correctAmount", countOfCorrect);
        model.addAttribute("totalAmount", totalAnswers);
        model.addAttribute("allResponses", allResponseSessionId);
        model.addAttribute("percentageAmount", percentageOfCorrectAnswers);
        model.addAttribute("questions", questions);
        model.addAttribute("user", user);
        return "quizCheck";
    }
}
